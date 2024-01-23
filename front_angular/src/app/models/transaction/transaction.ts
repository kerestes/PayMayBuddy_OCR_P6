import { Portefeuille } from "../portefeuille/portefeuille";

export class Transaction {
  constructor(
    public portefeuilleOrigine:Portefeuille,
    public portefeuilleDestination:Portefeuille,
    public montant:number,
    public dateTransaction:Date
  ){}
}
