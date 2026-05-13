import { Routes } from '@angular/router';

import { HomeComponent } from './components/home/home';
import { FeiranteComponent } from './components/feirante/feirante';
import { CategoriaComponent } from './components/categoria/categoria';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'feirantes',
    component: FeiranteComponent
  },
  {
    path: 'categorias',
    component: CategoriaComponent
  }
];