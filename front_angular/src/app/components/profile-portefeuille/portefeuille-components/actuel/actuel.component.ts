import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';

export interface HistoriqueTransaction {
  origine: string;
  montant: number;
  operation: string,
  date: string;
}

const BUDDYDETAILS: HistoriqueTransaction[] = [
  {origine: 'Société Général', montant: 150.00, operation: 'Dêpot', date: '2024/01/01 10H32'},
  {origine: 'Credit Agricole', montant: 28.97, operation: 'Retrait', date: '2023/11/21 16H13'},
  {origine: 'PayPal', montant: 32.50, operation: 'Dêpot', date: '2023/10/11 14H07'},
  {origine: 'PayPal', montant: 214.50, operation: 'Retrait', date: "2023/10/01 22H52"},
  {origine: 'PayPal', montant: 500.00, operation: 'Dêpot', date: '2023/08/01 01H02'},
  {origine: 'Credit Agricole', montant: 100.00, operation: 'Dêpot', date: '2023/07/30 11H27'}
];

@Component({
  selector: 'app-actuel',
  standalone: true,
  imports: [MatCardModule, MatTableModule, MatButtonModule],
  templateUrl: './actuel.component.html',
  styleUrl: './actuel.component.css'
})
export class ActuelComponent {
  displayedColumns: string[] = ['origine', 'montant', 'operation', 'date'];
  dataSource = BUDDYDETAILS;
}
