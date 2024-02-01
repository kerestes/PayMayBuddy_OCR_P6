import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../models/user/user';

export const BUDDY_URL: string = "http://localhost:8080/buddy"

@Injectable({
  providedIn: 'root'
})
export class BuddyService {

  constructor(private http: HttpClient) { }

  getBuddyList(){
    return this.http.get<Array<User>>(`${BUDDY_URL}/buddy-list`);
  }

  chercherBuddy(email:any){
    return this.http.post(`${BUDDY_URL}/trouver-buddy`, email);
  }

  ajouterBuddy(newBuddy:User){
    return this.http.post(`${BUDDY_URL}/add-buddy`, newBuddy);
  }
}
