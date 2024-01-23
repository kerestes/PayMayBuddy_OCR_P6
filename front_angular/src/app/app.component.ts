import { Component, inject } from '@angular/core';
import { CommonModule, DOCUMENT } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'
import { RouterLink } from '@angular/router';
import { LoginService } from './services/loginService/login.service';
import {MatMenuModule} from '@angular/material/menu';
import { Portefeuille } from './models/portefeuille/portefeuille';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, MatToolbarModule, MatButtonModule, MatIconModule, MatMenuModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  document:Document = inject(DOCUMENT);
  localStorage = this.document.defaultView?.localStorage;
  loginService: LoginService = inject(LoginService);
  title:string = 'front_angular';
  login:boolean = this.loginService.isAuthencitated();

  logout():void{
    if(this.localStorage){
      const token = localStorage.removeItem('token');
      window.location.replace("http://localhost:4200");
    }
  };
}
