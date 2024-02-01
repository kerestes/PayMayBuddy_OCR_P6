import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { RetraitService } from '../../../../services/retraitService/retrait.service';
import { MatDialog } from '@angular/material/dialog';
import { RetraitConfirmedComponent } from '../../../../dialogs/retrait-confirmed/retrait-confirmed.component';

@Component({
  selector: 'app-retirer',
  standalone: true,
  imports: [MatCardModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './retirer.component.html',
  styleUrl: './retirer.component.css'
})
export class RetirerComponent {

  retraitService:RetraitService = inject(RetraitService);
  dialog:MatDialog = inject(MatDialog);

  retrait = new FormGroup({
    montant: new FormControl(0, [Validators.required, Validators.min(1)]),
    iban: new FormControl('', [Validators.required]),
    bic: new FormControl('', [Validators.required])
  });

  ibanController(){
    let iban = this.retrait.get('iban')?.value
    if(typeof iban === 'string'){
      if(iban.length <=2 && new RegExp(/^[^A-Z]+$/).test(iban.substring(iban.length-1))){
        console.log('entrou2')
        this.retrait.patchValue({
          iban: iban.substring(0, iban.length -1)
        })
      } else if (iban.length > 2 && new RegExp(/^\D$/).test(iban.substring(iban.length-1))){
        console.log('entrou3')
        this.retrait.patchValue({
          iban: iban.substring(0, iban.length -1)
        })
      }
    }
  }

  bicController(){
    let bic = this.retrait.get('bic')?.value
    if(typeof bic === 'string' && new RegExp(/^[^A-z]+$/).test(bic.substring(bic.length-1))){
      this.retrait.patchValue({
        bic: bic.substring(0, bic.length -1)
      })
    }
  }

  deposer(){
    this.retraitService.makeRetrait(this.retrait.value).subscribe({
      next:(response) => {
        this.dialog.open(RetraitConfirmedComponent);
      },
      error:(error)=>{
        console.log(error);
      }
    })
  }
}
