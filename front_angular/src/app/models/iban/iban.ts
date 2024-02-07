import { Portefeuille } from "../portefeuille/portefeuille";

export class Iban {
  constructor(
    public portefeuille?:Portefeuille,
    public codeBanque?:string,
    public codeGuichet?:string,
    public numeroCompte?:string,
    public cleRib?:string,
    public iban?:string,
    public bic?:string
  ){}
}
