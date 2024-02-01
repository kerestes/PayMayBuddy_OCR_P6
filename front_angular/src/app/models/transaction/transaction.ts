import { Portefeuille } from "../portefeuille/portefeuille";

export class Transaction {
  constructor(
    public portefeuilleOrigine?:Portefeuille,
    public portefeuilleDestination?:Portefeuille,
    public montantTotal?:number | null,
    public taxe?:number,
    public montantLiquide?:number,
    public dateTransaction?:Date
  ){}
}
