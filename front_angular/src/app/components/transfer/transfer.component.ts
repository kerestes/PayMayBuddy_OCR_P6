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

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, RouterLink, MatInputModule, MatSelectModule, MatTableModule],
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

  makeTransfer(){
    if(this.selectedUser){
      this.transferService.user=this.selectedUser;
    }

    this.dialog.open(TransferDialogComponent);
  }
}
