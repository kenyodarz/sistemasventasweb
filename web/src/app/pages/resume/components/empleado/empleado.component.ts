import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  UntypedFormGroup,
  UntypedFormControl,
  ReactiveFormsModule,
  FormsModule,
  Validators,
  UntypedFormBuilder,
} from '@angular/forms';
import { Router } from '@angular/router';

import { ConfirmationService, MessageService } from 'primeng/api';
import { EmpleadoService } from 'src/app/core/services/empleado.service';
import { Empleado } from 'src/app/core/models/empleado';

// PrimeNG Módulos utilizados en la plantilla (agrega o ajusta según necesidad real)
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-empleado',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TableModule,
    ButtonModule,
    InputTextModule,
    DialogModule,
    ConfirmDialogModule,
    ToastModule,
    CardModule,
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './empleado.component.html',
  styleUrls: ['./empleado.component.css'],
})
export class EmpleadoComponent implements OnInit {
  empleado: Empleado = new Empleado();
  seledtedEmpleado: Empleado = null;
  empleados: Empleado[] = [];
  formEmpleado: UntypedFormGroup | undefined;

  constructor(
    private empleadoService: EmpleadoService,
    private router: Router,
    private fb: UntypedFormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.obtenerEmpleados();
    this.formEmpleado = this.fb.group({
      idEmpleado: new UntypedFormControl(),
      dni: new UntypedFormControl(null, Validators.required),
      nombres: new UntypedFormControl(null, Validators.required),
      telefono: new UntypedFormControl(),
      estado: new UntypedFormControl(),
      user: new UntypedFormControl(null, Validators.required),
    });
  }

  obtenerEmpleados() {
    this.empleadoService.getAll().subscribe((array: Empleado[]) => {
      this.empleados = array.sort((a, b) => a.nombres.localeCompare(b.nombres));
    });
  }

  guardarEmpleado() {
    this.empleadoService.save(this.empleado).subscribe((empleado: Empleado) => {
      this.messageService.add({
        severity: 'success',
        summary: '¡Correcto!',
        detail: `El empleado ${empleado.nombres} ha sido guardado correctamente`,
      });
      this.validarEmpleado(empleado);
    });
  }

  validarEmpleado(empleado: Empleado) {
    const index = this.empleados.findIndex(
      (e) => e.idEmpleado === empleado.idEmpleado
    );
    if (index !== -1) {
      this.empleados[index] = empleado;
    } else {
      this.empleados.push(empleado);
    }
    this.formEmpleado.reset();
  }

  guardarEditarEmpleado(editar: Boolean) {
    if (editar) {
      if (this.seledtedEmpleado?.idEmpleado != null) {
        this.formEmpleado.patchValue(this.seledtedEmpleado);
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: '¡¡¡Advertencia!!!',
          detail: 'No ha seleccionado ningun empleado',
        });
        return;
      }
    } else {
      this.empleado = new Empleado();
    }
  }

  eliminarEmpleado() {
    if (this.seledtedEmpleado?.idEmpleado == null) {
      this.messageService.add({
        severity: 'warn',
        summary: '¡¡¡Advertencia!!!',
        detail: 'No ha seleccionado ningun empleado',
      });
      return;
    }

    this.confirmationService.confirm({
      message: `¿Está seguro que desea eliminar el empleado ${this.seledtedEmpleado.nombres}?`,
      accept: () => {
        this.empleadoService
          .delete(this.seledtedEmpleado.idEmpleado)
          .subscribe((empleado: Empleado) => {
            this.messageService.add({
              severity: 'info',
              summary: 'Información',
              detail: `El empleado ${empleado.nombres} ha sido eliminado correctamente`,
            });
            this.validarEliminar(empleado);
          });
      },
    });
  }

  validarEliminar(empleado: Empleado) {
    const index = this.empleados.findIndex(
      (e) => e.idEmpleado === empleado.idEmpleado
    );
    if (index !== -1) {
      this.empleados.splice(index, 1);
    }
  }

  onEliminar(empleado: Empleado) {
    this.seledtedEmpleado = empleado;
    this.eliminarEmpleado();
  }

  onEditar(empleado: Empleado) {
    this.seledtedEmpleado = empleado;
    this.guardarEditarEmpleado(true);
  }

  onCancelar() {
    this.formEmpleado.reset();
    this.seledtedEmpleado = null;
  }

  onGuardar() {
    this.empleado = this.formEmpleado.value;
    this.guardarEmpleado();
  }
}
