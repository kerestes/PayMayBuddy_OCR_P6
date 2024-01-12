import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrerComponent } from './components/registrer/registrer.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { BuddysComponent } from './components/buddys/buddys.component';
import { ProfileConfigComponent } from './components/profile-config/profile-config.component';
import { ProfilePortFeuilleComponent } from './components/profile-portefeuille/profile-portefeuille.component';
import { ActuelComponent } from './components/profile-portefeuille/portefeuille-components/actuel/actuel.component';
import { DepotComponent } from './components/profile-portefeuille/portefeuille-components/depot/depot.component';
import { RetirerComponent } from './components/profile-portefeuille/portefeuille-components/retirer/retirer.component';

export const routes: Routes = [
  {path:'', redirectTo:'/home', pathMatch:'full'},
  {path:'home', component: HomeComponent},
  {path:'login', component: LoginComponent},
  {path:'registrer', component: RegistrerComponent},
  {path:'transfer', component: TransferComponent},
  {path:'buddys', component: BuddysComponent},
  {path:'profile/configuration', component: ProfileConfigComponent},
  {path: 'profile/portefeuille',
  component: ProfilePortFeuilleComponent,
  children:[
    {path:'', redirectTo:'/profile/portefeuille/actuel', pathMatch:'full'},
    {path:'actuel', component: ActuelComponent},
    {path:'depot', component: DepotComponent},
    {path:'retirer', component: RetirerComponent}
  ]}
];
