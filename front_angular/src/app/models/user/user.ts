import { Portefeuille } from "../portefeuille/portefeuille";

export class User {
  constructor(
    public idUser:string,
    public prenom:string,
    public nom:string,
    public email:string,
    public adresse:string,
    public password:string,
    public confirmPassword:string
  ){}

}
