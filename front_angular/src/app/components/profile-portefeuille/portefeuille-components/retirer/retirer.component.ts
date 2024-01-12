import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-retirer',
  standalone: true,
  imports: [MatCardModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './retirer.component.html',
  styleUrl: './retirer.component.css'
})
export class RetirerComponent {

  retrait = new FormGroup({
    montant: new FormControl(null, [Validators.required]),
    iban: new FormControl(null, [Validators.required])
  });

}
