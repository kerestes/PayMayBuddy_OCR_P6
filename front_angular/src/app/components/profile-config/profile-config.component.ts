import { Component, inject } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@angular/flex-layout';
import {FlexLayoutServerModule} from '@angular/flex-layout/server';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { PortefeuilleService } from '../../services/portefeuille/portefeuille.service';
import { User } from '../../models/user/user';
import { response } from 'express';
import { ProfilService } from '../../services/profilService/profil.service';

@Component({
  selector: 'app-profile-config',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, FlexLayoutModule, FlexLayoutServerModule, MatButtonModule, MatIconModule, ReactiveFormsModule],
  templateUrl: './profile-config.component.html',
  styleUrl: './profile-config.component.css'
})
export class ProfileConfigComponent {

  profilService:ProfilService = inject(ProfilService);
  user:User = new User();


  profilForm = new FormGroup({
    nom: new FormControl('', [Validators.required]),
    prenom: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    adresse: new FormControl('', [Validators.required]),
    ville: new FormControl('', [Validators.required]),
    codePostal: new FormControl('', [Validators.required])
  });

  ngOnInit(){
    this.profilService.getUser().subscribe({
      next:(response) =>{
        this.profilForm.patchValue({
          nom: response.nom,
          prenom: response.prenom,
          email: response.email,
          adresse: response.adresse,
          ville: response.ville,
          codePostal: response.codePostal
        })
      }
    })
  }

  save(){
    this.profilService.saveUser(this.user).subscribe({
      error:(error) => {
        console.log(error);
      }
    })
  }

}
