import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../models/user/user';

export const REGISTER_URL: string = "http://localhost:8080/buddy-list"

@Injectable({
  providedIn: 'root'
})
export class BuddyService {

  constructor(private http: HttpClient) { }

  getBuddyList(){
    return this.http.get<Array<User>>(REGISTER_URL);
  }
}
