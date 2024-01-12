import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';

export interface BuddyDetails {
  nom: string;
  prenom: string;
  adresse: string;
}

const BUDDYDETAILS: BuddyDetails[] = [
  {nom: 'Martin', prenom: 'Gabreil', adresse: '15 square du roi, Paris'},
  {nom: 'Bernard', prenom: 'Léo', adresse: '43 rue du benevole, Angoulême'},
  {nom: 'Dubois', prenom: 'Raphaël', adresse: '11 avenue Saint-Étienne, Limoges'},
  {nom: 'Thomas', prenom: 'Louis', adresse: "5 boulevard de l'ouest, Saint-Maur"},
  {nom: 'Robert', prenom: 'Arthur', adresse: '60 rue Saint-Antoine, Saint-Yerix'},
  {nom: 'Richard', prenom: 'Jules', adresse: '47 avenue Beaublanc, Limoges'},
  {nom: 'Petit', prenom: 'Maël', adresse: '32 rue du demain, Bordeaux'},
  {nom: 'Durand', prenom: 'Noah', adresse: '70 Boulevard du sac à dos, Ville-Riche'},
  {nom: 'Leroy', prenom: 'Aadam', adresse: "2 Square de l'avenir, Crouzeix"},
  {nom: 'Moreau', prenom: 'Lucas', adresse: '4 rue Ça va ça viens, Lyon'}
];

@Component({
  selector: 'app-buddys',
  standalone: true,
  imports: [MatCardModule, MatTableModule, MatButtonModule, RouterLink],
  templateUrl: './buddys.component.html',
  styleUrl: './buddys.component.css'
})
export class BuddysComponent {
  displayedColumns: string[] = ['nom', 'prenom', 'adresse'];
  dataSource = BUDDYDETAILS;
}
