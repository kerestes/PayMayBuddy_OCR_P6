import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDividerModule} from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';
import { Carte } from '../../../../models/carte/carte';
import { Depot } from '../../../../models/depot/depot';
import { DepotService } from '../../../../services/depotService/depot.service';
import { MatDialog } from '@angular/material/dialog';
import { DepotConfirmedComponent } from '../../../../dialogs/depot-confirmed/depot-confirmed.component';

export interface FormatRib {
  codeBanque: string;
  codeGuichet: string;
  numero: string,
  cleRib: string;
}

const RIBDETAILS: FormatRib[] =[{codeBanque: '99999', codeGuichet: '1000', numero: '0123456789', cleRib: '00'}];

@Component({
  selector: 'app-depot',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatDividerModule, MatTableModule],
  templateUrl: './depot.component.html',
  styleUrl: './depot.component.css'
})
export class DepotComponent {

  depotService:DepotService = inject(DepotService);
  dialog:MatDialog = inject(MatDialog);

  annee:number = new Date().getFullYear();

  cardPaiement = new FormGroup({
    nomCarte: new FormControl(null, [Validators.required]),
    montant: new FormControl(0, [Validators.required, Validators.min(1)]),
    numeroCarte: new FormControl('', [Validators.required, Validators.minLength(16)]),
    mois: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(12)]),
    annee: new FormControl(null, [Validators.required, Validators.min(this.annee) ]),
    cvv: new FormControl('', [Validators.required, Validators.maxLength(3), Validators.minLength(3)])
  });

  displayedColumns: string[] = ['codeBanque', 'codeGuichet', 'numero', 'cleRib'];
  dataSource = RIBDETAILS;

  iban:string= "FR75 " + RIBDETAILS[0].codeBanque + " " + RIBDETAILS[0].codeGuichet + " " + RIBDETAILS[0].numero + " " + RIBDETAILS[0].cleRib;

  bic:string = "BUDBANFRXXX"

  formtInput(){
    let num:string | null | undefined = this.cardPaiement.get('numeroCarte')?.value
    if(typeof num === 'string' && (num.length == 4 || num.length == 9 || num.length == 14) ){
      num+=' ';
      this.cardPaiement.patchValue({
        numeroCarte: num
      })
    } else if (typeof num === 'string' && (num.length < 4 || (num.length > 5 && num.length < 14) || num.length > 14)){
      if(new RegExp(/^\D$/).test(num.substring(num.length -1))){
        this.cardPaiement.patchValue({
          numeroCarte: num.substring(0, num.length -1)
        })
      }
    }
  }

  controlCvv(){
    let cvvVar = this.cardPaiement.get('cvv')?.value
    if(typeof cvvVar === 'string' && new RegExp(/^\D$/).test(cvvVar.substring(cvvVar.length -1))){
      this.cardPaiement.patchValue({
        cvv: cvvVar.substring(0, cvvVar.length -1)
      })
    }
  }

  deposer(){
    if(this.cardPaiement.controls.nomCarte.valid && this.cardPaiement.controls.numeroCarte.valid &&
      this.cardPaiement.controls.mois.valid && this.cardPaiement.controls.annee.valid && this.cardPaiement.controls.cvv.valid){
        this.depotService.makeDepot(this.cardPaiement.value).subscribe({
          next:(response) => {
            this.dialog.open(DepotConfirmedComponent);
          },
          error:(error) => {
            console.log(error);
          }
        });
      }
  }

}
