import { HttpClient } from '@angular/common/http';
import { DOCUMENT } from '@angular/common';
import { Injectable, inject } from '@angular/core';
import { JwtHelperService} from '@auth0/angular-jwt';
import { catchError } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { Login403Component } from '../../dialogs/login-403/login-403.component';
import { Login404Component } from '../../dialogs/login-404/login-404.component';

export const LOGIN_URL: string = "http://localhost:8080/login"

export const jwtHelper = new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  document:Document = inject(DOCUMENT);
  localStorage = this.document.defaultView?.localStorage;
  private dialog:MatDialog=inject(MatDialog);

  constructor(private http:HttpClient) { }

  isAuthencitated():boolean{
    if(this.localStorage){
      const token = localStorage.getItem("token");
      return !jwtHelper.isTokenExpired(token);
    }
    return false;
  }

  submit(loginCredential: any){
    return this.http.post(LOGIN_URL, loginCredential, { responseType: 'text' }).pipe(
      catchError((error) => {
        if(error.status === 403){
          this.dialog.open(Login403Component);
        } else {
          this.dialog.open(Login404Component);
        }
        return '';
      })
    );
  }

  getToken(){
    if(this.localStorage){
      return localStorage.getItem("token");
    }
    return null;
  }
}
