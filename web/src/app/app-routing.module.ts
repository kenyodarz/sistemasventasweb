import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
/* Guards */
import { LoginGuard } from './guards/login.guard';
/* Components */
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  {
    path: 'resume',
    loadChildren: () =>
      import('src/app/pages/resume/resume.module').then((m) => m.ResumeModule),
    canActivate: [LoginGuard],
  },
  { path: 'login', component: LoginComponent },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'resume',
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      useHash: true,
      onSameUrlNavigation: 'reload',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
