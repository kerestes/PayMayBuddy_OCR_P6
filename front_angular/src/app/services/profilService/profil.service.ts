import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../models/user/user';

const URL = "http://localhost:8080/user";

@Injectable({
  providedIn: 'root'
})
export class ProfilService {

  constructor(private http:HttpClient) { }

  public getUser():Observable<User>{
    return this.http.get<User>(URL);
  }

  public saveUser(user:User):Observable<User>{
    return this.http.post<User>(URL, user);
  }
}
