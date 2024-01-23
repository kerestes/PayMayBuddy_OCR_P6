import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { BuddyService } from '../../services/buddyService/buddy.service';
import { User } from '../../models/user/user';

@Component({
  selector: 'app-buddys',
  standalone: true,
  imports: [MatCardModule, MatTableModule, MatButtonModule, RouterLink],
  templateUrl: './buddys.component.html',
  styleUrl: './buddys.component.css'
})
export class BuddysComponent {
  buddyService:BuddyService = inject(BuddyService);
  displayedColumns: string[] = ['nom', 'prenom', 'email', 'idUser'];
  dataSource!:User[];

  ngOnInit(){
    this.buddyService.getBuddyList().subscribe({
      next:(response)=>{
        this.dataSource = response;
      }
    })
  }

  modalTransfer(idUser:string){

  }
}
