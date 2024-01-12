import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  login: boolean = true;

  constructor() { }

  getLogin():boolean{
    return this.login;
  }

  setLogin(login:boolean){
    this.login = login;
  }
}
