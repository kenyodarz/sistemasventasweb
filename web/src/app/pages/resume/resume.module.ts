import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PrimengModule } from 'src/app/shared/primeng.module';
import { ResumeRoutingModule } from './resume-routing.module';
import { ResumeComponent } from './resume.component';
import { HomeComponent } from './components/home/home.component';
import { ClientesComponent } from './components/clientes/clientes.component';
import { EmpleadoComponent } from './components/empleado/empleado.component';
import { ProductosComponent } from './components/productos/productos.component';
import { RegistrarVentaComponent } from './components/registrar-venta/registrar-venta.component';

@NgModule({
  declarations: [ResumeComponent, HomeComponent, ClientesComponent, EmpleadoComponent, ProductosComponent, RegistrarVentaComponent],
  imports: [
    CommonModule,
    ResumeRoutingModule,
    PrimengModule,
    FormsModule,
    ReactiveFormsModule,
  ],
})
export class ResumeModule {}
