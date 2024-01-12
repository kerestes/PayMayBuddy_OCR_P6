import { Component } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import {FlexLayoutServerModule} from '@angular/flex-layout/server';
import { RouterOutlet } from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTabNav } from '@angular/material/tabs';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-profile-port-feuille',
  standalone: true,
  imports: [MatTabsModule, FlexLayoutModule, FlexLayoutServerModule, RouterOutlet, RouterLink],
  providers:[MatTabNav],
  templateUrl: './profile-portefeuille.component.html',
  styleUrl: './profile-portefeuille.component.css'
})
export class ProfilePortFeuilleComponent {
  labelLinkRouters = [
    {label: 'Mon portefeuille', link: 'actuel'},
    {label: "Déposer d'argent", link: 'depot'},
    {label: "Retirer de l'argent", link: 'retirer'},
  ]
}
