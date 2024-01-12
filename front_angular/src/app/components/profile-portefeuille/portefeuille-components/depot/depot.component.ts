import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDividerModule} from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';

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

  cardPaiement = new FormGroup({
    montant: new FormControl(null, [Validators.required]),
    numeroCarte: new FormControl(null, [Validators.required]),
    mois: new FormControl(null, [Validators.required]),
    annee: new FormControl(null, [Validators.required]),
    cvv: new FormControl(null, [Validators.required])
  });

  displayedColumns: string[] = ['codeBanque', 'codeGuichet', 'numero', 'cleRib'];
  dataSource = RIBDETAILS;

  iban:string= "FR75 " + RIBDETAILS[0].codeBanque + " " + RIBDETAILS[0].codeGuichet + " " + RIBDETAILS[0].numero + " " + RIBDETAILS[0].cleRib;

  bic:string = "BUDBANFRXXX"

}
