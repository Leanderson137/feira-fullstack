import { Routes } from '@angular/router';

import { HomeComponent } from './components/home/home';
import { FeiranteComponent } from './components/feirante/feirante';
import { CategoriaComponent } from './components/categoria/categoria';
import { LoginComponent } from './components/login/login';

import { authGuard } from './guards/auth.guard';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard]
  },

  {
    path: 'feirantes',
    component: FeiranteComponent,
    canActivate: [authGuard]
  },

  {
    path: 'categorias',
    component: CategoriaComponent,
    canActivate: [authGuard]
  }
];