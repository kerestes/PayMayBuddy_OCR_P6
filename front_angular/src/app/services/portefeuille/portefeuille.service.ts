import { Injectable } from '@angular/core';
import { Portefeuille } from '../../models/portefeuille/portefeuille';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const URL = "http://localhost:8080/portefeuille";

@Injectable({
  providedIn: 'root'
})
export class PortefeuilleService {

  constructor(private http:HttpClient) { }

  public getPortefeuille():Observable<Portefeuille>{
    return this.http.get<Portefeuille>(URL)
  }
}
