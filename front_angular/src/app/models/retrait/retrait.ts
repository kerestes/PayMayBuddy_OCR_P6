import { Iban } from "../iban/iban";
import { Portefeuille } from "../portefeuille/portefeuille";


export class Retrait {
  constructor(
    public portefeuille:Portefeuille,
    public iban:Iban,
    public montant:number,
    public dateRetrait:Date
  ){}
}
