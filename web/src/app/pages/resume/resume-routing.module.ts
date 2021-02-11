import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard } from 'src/app/guards/login.guard';
import { ClientesComponent } from './components/clientes/clientes.component';
import { EmpleadoComponent } from './components/empleado/empleado.component';
import { HomeComponent } from './components/home/home.component';
import { ProductosComponent } from './components/productos/productos.component';
import { RegistrarVentaComponent } from './components/registrar-venta/registrar-venta.component';
import { ResumeComponent } from './resume.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
    canActivate: [LoginGuard],
  },
  {
    path: '',
    component: ResumeComponent,
    children: [
      {
        path: 'home',
        component: HomeComponent,
        canActivate: [LoginGuard],
      },
      {
        path: 'clientes',
        component: ClientesComponent,
        canActivate: [LoginGuard],
      },
      {
        path: 'productos',
        component: ProductosComponent,
        canActivate: [LoginGuard],
      },
      {
        path: 'empleado',
        component: EmpleadoComponent,
        canActivate: [LoginGuard],
      },
      {
        path: 'ventas',
        component: RegistrarVentaComponent,
        canActivate: [LoginGuard],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ResumeRoutingModule { }
