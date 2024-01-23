import { Carte } from "../carte/carte";
import { Depot } from "../depot/depot";
import { Iban } from "../iban/iban";
import { Retrait } from "../retrait/retrait";
import { Transaction } from "../transaction/transaction";
import { User } from "../user/user";

export class Portefeuille {
  constructor(
    public user:User,
    public solde:number,
    public updateDate:Date,
  ){}
}
