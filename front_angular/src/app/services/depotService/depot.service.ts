import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Depot } from '../../models/depot/depot';
import { Observable } from 'rxjs';

const URL = "http://localhost:8080/depot";

@Injectable({
  providedIn: 'root'
})
export class DepotService {

  constructor(private http:HttpClient) { }

  public getDepots():Observable<Depot[]>{
    return this.http.get<Depot[]>(URL);
  }
}
