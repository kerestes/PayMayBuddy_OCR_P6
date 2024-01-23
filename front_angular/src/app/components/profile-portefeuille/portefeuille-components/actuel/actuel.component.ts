import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { PortefeuilleService } from '../../../../services/portefeuille/portefeuille.service';
import { Depot } from '../../../../models/depot/depot';
import { Retrait } from '../../../../models/retrait/retrait';
import { DepotService } from '../../../../services/depotService/depot.service';
import { nextTick } from 'process';
import { RetraitService } from '../../../../services/retraitService/retrait.service';


@Component({
  selector: 'app-actuel',
  standalone: true,
  imports: [MatCardModule, MatTableModule, MatButtonModule],
  templateUrl: './actuel.component.html',
  styleUrl: './actuel.component.css'
})
export class ActuelComponent {
  portefeuilleService: PortefeuilleService = inject(PortefeuilleService);
  depotService: DepotService = inject(DepotService);
  retraitService: RetraitService = inject(RetraitService);
  solde!:number;
  displayedColumns: string[] = ['carte', 'iban', 'montant', 'date'];
  displayedColumnsRetrait: string[] =['iban', 'montant', 'date']
  dataSourceDepot!: Depot[];
  dataSourceRetrait!: Retrait[]

  ngOnInit(){
    this.portefeuilleService.getPortefeuille().subscribe({
      next:(response) => {
        this.solde = response.solde;
      }
    })
    this.depotService.getDepots().subscribe({
      next:(response)=>{
        this.dataSourceDepot = response;
      }
    })
    this.retraitService.getRetraits().subscribe({
      next:(response) =>{
        this.dataSourceRetrait = response;
      }
    })
  }
}
