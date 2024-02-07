import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { LoginService } from '../../services/loginService/login.service';
import { MatDialog } from '@angular/material/dialog';
import { Login403Component } from '../../dialogs/login-403/login-403.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSnackBarModule,
    RouterLink,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  matSnackBar = inject(MatSnackBar);
  loginService: LoginService = inject(LoginService);
  router:Router = inject(Router);
  private dialog:MatDialog=inject(MatDialog);

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  hidePassword:boolean = true;

  tooglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;
  }

  onSubmit(){
    this.loginService.submit(this.loginForm.value).subscribe({
      next:(response) => {
        if(typeof response === "string"){
          localStorage.setItem('token', response);
          window.location.replace("http://localhost:4200");
          return;
        }
        if(response == 403){
          this.dialog.open(Login403Component);
        }
      }
    });
  }

}
