import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { BuddyService } from '../../services/buddyService/buddy.service';
import { User } from '../../models/user/user';
import { MatDialog } from '@angular/material/dialog';
import { AddBuddyComponent } from '../../dialogs/add-buddy/add-buddy.component';
import { TransferDialogComponent } from '../../dialogs/transfer-dialog/transfer-dialog.component';
import { TrasferService } from '../../services/transferService/trasfer.service';

@Component({
  selector: 'app-buddys',
  standalone: true,
  imports: [MatCardModule, MatTableModule, MatButtonModule, RouterLink],
  templateUrl: './buddys.component.html',
  styleUrl: './buddys.component.css'
})
export class BuddysComponent {
  private dialog:MatDialog=inject(MatDialog);
  buddyService:BuddyService = inject(BuddyService);
  transferService: TrasferService = inject(TrasferService);

  displayedColumns: string[] = ['nom', 'prenom', 'email', 'idUser'];
  dataSource!:User[];

  ngOnInit(){
    this.buddyService.getBuddyList().subscribe({
      next:(response)=>{
        this.dataSource = response;
      }
    })
  }

  modalTransfer(user:User){
    this.transferService.user=user;
    this.dialog.open(TransferDialogComponent)
  }

  addBuddyDialog(){
    this.dialog.open(AddBuddyComponent);
  }
}
