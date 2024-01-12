import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@angular/flex-layout';
import {FlexLayoutServerModule} from '@angular/flex-layout/server';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-profile-config',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, FlexLayoutModule, FlexLayoutServerModule, MatButtonModule, MatIconModule],
  templateUrl: './profile-config.component.html',
  styleUrl: './profile-config.component.css'
})
export class ProfileConfigComponent {

}
