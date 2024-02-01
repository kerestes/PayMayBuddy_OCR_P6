import { Component, inject } from '@angular/core';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BuddyService } from '../../services/buddyService/buddy.service';
import { User } from '../../models/user/user';

@Component({
  selector: 'app-add-buddy',
  standalone: true,
  imports: [
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatButtonModule,
    RouterLink,
    MatCardModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './add-buddy.component.html',
  styleUrl: './add-buddy.component.css'
})
export class AddBuddyComponent {

  buddyService: BuddyService = inject(BuddyService);
  newBuddy:User = new User();

  chercherBuddyForm= new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email])
  });

  onSubmit(){
    this.buddyService.chercherBuddy(this.chercherBuddyForm.value).subscribe({
      next:(response)=>{
        this.newBuddy=response;
      },
      error:(error) => {
        this.chercherBuddyForm.get('email')?.setValue("");
      }
    })
  }

  confirmer(){
    this.buddyService.ajouterBuddy(this.newBuddy).subscribe({
      next:(response)=>{
        window.location.replace("http://localhost:4200/buddys");
      }
    })
  }

}
