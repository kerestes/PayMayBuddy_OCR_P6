import { Portefeuille } from "../portefeuille/portefeuille";

export class User {
  constructor(
    public id?:number,
    public idUser?:string,
    public prenom?:string,
    public nom?:string,
    public email?:string | null,
    public adresse?:string,
    public ville?:string,
    public codePostal?:string,
    public password?:string,
    public confirmPassword?:string
  ){}

}
