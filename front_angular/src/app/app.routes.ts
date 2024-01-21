import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { BuddysComponent } from './components/buddys/buddys.component';
import { ProfileConfigComponent } from './components/profile-config/profile-config.component';
import { ProfilePortFeuilleComponent } from './components/profile-portefeuille/profile-portefeuille.component';
import { ActuelComponent } from './components/profile-portefeuille/portefeuille-components/actuel/actuel.component';
import { DepotComponent } from './components/profile-portefeuille/portefeuille-components/depot/depot.component';
import { RetirerComponent } from './components/profile-portefeuille/portefeuille-components/retirer/retirer.component';
import { authGuardGuard } from './guards/auth-guard.guard';

export const routes: Routes = [
  {path:'', redirectTo:'/home', pathMatch:'full'},
  {path:'home', component: HomeComponent},
  {path:'login', component: LoginComponent},
  {path:'registrer', component: RegisterComponent},
  {path:'transfer', canActivate: [authGuardGuard], component: TransferComponent},
  {path:'buddys', canActivate: [authGuardGuard], component: BuddysComponent},
  {path:'profile/configuration', canActivate: [authGuardGuard], component: ProfileConfigComponent},
  {path: 'profile/portefeuille',
  canActivate: [authGuardGuard],
  canActivateChild: [() => true],
  component: ProfilePortFeuilleComponent,
  children:[
    {path:'', redirectTo:'/profile/portefeuille/actuel', pathMatch:'full'},
    {path:'actuel', component: ActuelComponent},
    {path:'depot', component: DepotComponent},
    {path:'retirer', component: RetirerComponent}
  ]}
];
