import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaction } from '../../models/transaction/transaction';
import { Observable } from 'rxjs';

export const REGISTER_URL: string = "http://localhost:8080/transfer-list"

@Injectable({
  providedIn: 'root'
})
export class TrasferService {

  constructor(private http: HttpClient) { }

  getTransferList():Observable<Transaction[]>{
    return this.http.get<Transaction[]>(REGISTER_URL);
  }
}
