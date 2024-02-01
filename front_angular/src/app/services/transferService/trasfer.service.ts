import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaction } from '../../models/transaction/transaction';
import { Observable } from 'rxjs';
import { User } from '../../models/user/user';

export const REGISTER_URL: string = "http://localhost:8080"

@Injectable({
  providedIn: 'root'
})
export class TrasferService {

  user:User = new User();
  transaction:Transaction = new Transaction();

  constructor(private http: HttpClient) { }

  getTransferList():Observable<Transaction[]>{
    return this.http.get<Transaction[]>(`${REGISTER_URL}/transfer-list`);
  }

  makeTransfer(){
    return this.http.post<Transaction>(`${REGISTER_URL}/make-transfer`, {email:this.user.email, montant:this.transaction.montantTotal});
  }
}
