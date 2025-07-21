import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'resume',
    loadChildren: () =>
      import('./pages/resume/resume-routing.module').then(
        (m) => m.ResumeRoutingModule
      ),
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];
