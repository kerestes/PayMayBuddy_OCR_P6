import { Component, inject } from '@angular/core';
import {
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { TrasferService } from '../../services/transferService/trasfer.service';
import { User } from '../../models/user/user';
import { Transaction } from '../../models/transaction/transaction';
import { SoldeInsuffisantComponent } from '../solde-inssuffisant/solde-insuffisant.component';

@Component({
  selector: 'app-transfer-dialog',
  standalone: true,
  imports: [
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatCardModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './transfer-dialog.component.html',
  styleUrl: './transfer-dialog.component.css'
})
export class TransferDialogComponent {
  transferService: TrasferService = inject(TrasferService);
  private dialog:MatDialog=inject(MatDialog);

  transferBuddy = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    montant: new FormControl(0, [Validators.required])
  });

  ngOnInit(){
    if(this.transferService.user.email)
      this.transferBuddy.get('email')?.setValue(this.transferService.user.email);
  }

  onSubmit(){
    if(this.transferBuddy.controls.email.valid && this.transferBuddy.controls.montant.valid){
      if(this.transferService.user.email != this.transferBuddy.get('email')?.value)
        console.log("escolha um buddy na lista")
      else
        this.transferService.transaction.montantTotal = this.transferBuddy.get('montant')?.value
    }
  }

  confirmer(){
    if(this.transferService.transaction.montantTotal && this.transferService.user.email){
      this.transferService.makeTransfer().subscribe({
        next:(response) =>{
          console.log("reload")
        },
        error:(error)=> {
          if(error.status == 406){
            this.dialog.open(SoldeInsuffisantComponent);
          }
          console.log(error)
        }
      })
    }
    this.vider();
  }

  vider(){
    this.transferService.user=new User();
    this.transferService.transaction=new Transaction();
  }

}
