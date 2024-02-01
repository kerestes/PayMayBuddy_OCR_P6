import { Carte } from "../carte/carte";
import { Iban } from "../iban/iban";
import { Portefeuille } from "../portefeuille/portefeuille";

export class Depot {
  constructor(
    public portefeuille?: Portefeuille,
    public carte?: Carte,
    public iban?: Iban,
    public montantTotal?:number,
    public taxe?:number,
    public montantLiquide?:number,
    public dateDepot?: Date
  ){}
}
