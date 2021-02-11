import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
/* Guards */
import { LoginGuard } from './guards/login.guard';
/* Components */
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'login',
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
