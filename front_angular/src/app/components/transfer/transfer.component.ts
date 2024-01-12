import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';


export interface BuddyTransfer {
  buddy: string;
  description: string;
  montant: number;
}

const BUDDYTRANSFER: BuddyTransfer[] = [
  {buddy: 'GABRIEL', description: 'Date: 2022-04-05', montant: 100.03},
  {buddy: 'LÉO', description: 'Date: 2022-04-05', montant: 204.50},
  {buddy: 'RAPHAËL', description: 'Date: 2022-04-05', montant: 302.70},
  {buddy: 'LOUIS', description: 'Date: 2022-04-05', montant: 902.00},
  {buddy: 'ARTHUR', description: 'Date: 2022-04-05', montant: 1000.00},
  {buddy: 'JULES', description: 'Date: 2022-04-05', montant: 2023.04},
  {buddy: 'MAËL', description: 'Date: 2022-04-05', montant: 14.00},
  {buddy: 'NOAH', description: 'Date: 2022-04-05', montant: 994.56},
  {buddy: 'ADAM', description: 'Date: 2022-04-05', montant: 84.00},
  {buddy: 'LUCAS', description: 'Date: 2022-04-05', montant: 20.67},
];

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, RouterLink, MatInputModule, MatSelectModule, MatTableModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  displayedColumns: string[] = ['buddy', 'description', 'montant'];
  dataSource = BUDDYTRANSFER;
}
