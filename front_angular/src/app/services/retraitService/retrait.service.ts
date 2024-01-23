import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Retrait } from '../../models/retrait/retrait';
import { Observable } from 'rxjs';

const URL = 'http://localhost:8080/retrait';

@Injectable({
  providedIn: 'root'
})
export class RetraitService {

  constructor(private http:HttpClient) { }

  public getRetraits():Observable<Retrait[]>{
    return this.http.get<Retrait[]>(URL);
  }
}
