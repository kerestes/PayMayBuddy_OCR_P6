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

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, RouterLink, MatInputModule, MatSelectModule, MatTableModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  selectOptions!:User[];
  buddyService:BuddyService = inject(BuddyService);
  transferList:TrasferService = inject(TrasferService);
  displayedColumns: string[] = ['userOrigine', 'userDestination', 'montant', 'dateTransaction'];
  dataSource!:Transaction[];

  ngOnInit(){
    this.transferList.getTransferList().subscribe({
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
}
