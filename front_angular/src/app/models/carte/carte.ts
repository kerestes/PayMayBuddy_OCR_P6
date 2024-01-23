import { Portefeuille } from "../portefeuille/portefeuille";

export class Carte {
  constructor(
    public portefeuille:Portefeuille,
    public nomCarte:string,
    public numCarte:string,
    public moisCarte:Date,
    public cryptogramme:number
  ){}
}
