import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';
import { TrasferService } from '../../services/transferService/trasfer.service';
import { Transaction } from '../../models/transaction/transaction';
import { User } from '../../models/user/user';
import { BuddyService } from '../../services/buddyService/buddy.service';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { TransferDialogComponent } from '../../dialogs/transfer-dialog/transfer-dialog.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, RouterLink, MatInputModule, MatSelectModule, MatTableModule,
    ReactiveFormsModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  buddyService:BuddyService = inject(BuddyService);
  transferService:TrasferService = inject(TrasferService);
  private dialog:MatDialog=inject(MatDialog);

  selectOptions!:User[];
  selectedUser?:User;

  displayedColumns: string[] = ['userOrigine', 'userDestination', 'montantTotal', 'taxe', 'montantLiquide', 'dateTransaction'];
  dataSource!:Transaction[];

  selectUserFrom = new FormGroup({
    buddy: new FormControl('', [Validators.required]),
    montant: new FormControl(0, [Validators.required])
  });

  ngOnInit(){
    this.transferService.getTransferList().subscribe({
      next:(response) => {
        this.dataSource = response;
      }
    })
    this.buddyService.getBuddyList().subscribe({
      next:(response)=>{
        this.selectOptions = response;
      }
    })
  }

  selectUser(user:User){
    this.selectedUser =user;
  }

  makeTransfer(){
    console.log(this.selectedUser)
    if(this.selectUserFrom.controls.buddy.valid && this.selectUserFrom.controls.montant.valid && this.selectedUser){
      this.transferService.user = this.selectedUser;
      this.transferService.transaction.montantTotal = this.selectUserFrom.get('montant')?.value;
    }

    this.dialog.open(TransferDialogComponent);
  }
}
