import { Iban } from "../iban/iban";
import { Portefeuille } from "../portefeuille/portefeuille";


export class Retrait {
  constructor(
    public portefeuille:Portefeuille,
    public iban:Iban,
    public montantTotal:number,
    public taxe:number,
    public montantLiquide:number,
    public dateRetrait:Date
  ){}
}
