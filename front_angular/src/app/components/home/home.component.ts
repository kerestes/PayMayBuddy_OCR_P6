import { Component, Input, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { LoginService } from '../../services/loginService/login.service';
import { RegisterService } from '../../services/registerService/register.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmeRegistrationComponent } from '../../dialogs/confirm-registration/confirme-registration/confirme-registration.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButtonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  loginService: LoginService= inject(LoginService);
  registerService: RegisterService = inject(RegisterService);
  private dialog:MatDialog=inject(MatDialog);
  private activetedRoute: ActivatedRoute= inject(ActivatedRoute);

  login:boolean = this.loginService.isAuthencitated();

  ngOnInit():void{
    let token = this.activetedRoute.snapshot.queryParamMap.get("token");
    if(token){
      this.registerService.confirmRegistration(token).subscribe({
        next:() => {
            this.dialog.open(ConfirmeRegistrationComponent);
        },
        error:(error) => {
          console.error(error);
        }
      })
    }
  }
}
