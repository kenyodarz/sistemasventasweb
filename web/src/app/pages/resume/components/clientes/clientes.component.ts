import { Component, OnInit } from '@angular/core';

import {
  UntypedFormGroup,
  UntypedFormControl,
  Validators,
  ReactiveFormsModule,
  UntypedFormBuilder,
} from '@angular/forms';
import { Router } from '@angular/router';

// PrimeNG
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

// Servicios
import { ClienteService } from 'src/app/core/services/cliente.service';
// Modelos
import { Cliente } from 'src/app/core/models/cliente';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    DialogModule,
    ConfirmDialogModule,
    ToastModule,
    CardModule,
    MessageModule
],
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],
  providers: [ConfirmationService, MessageService],
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  cliente: Cliente = new Cliente();
  selectedCliente: Cliente = null;
  formCliente: UntypedFormGroup;

  constructor(
    private readonly clienteService: ClienteService,
    private readonly router: Router,
    private readonly fb: UntypedFormBuilder,
    private readonly messageService: MessageService,
    private readonly confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.obtenerClientes();
    this.formCliente = this.fb.group({
      idCliente: new UntypedFormControl(),
      dni: new UntypedFormControl(null, Validators.required),
      nombres: new UntypedFormControl(null, Validators.required),
      direccion: new UntypedFormControl(null, Validators.required),
      estado: new UntypedFormControl(null, Validators.required),
    });
  }

  obtenerClientes() {
    this.clienteService.getAll().subscribe((array: Cliente[]) => {
      let clienteList: Cliente[] = [];
      array.forEach((element) => {
        clienteList.push(element);
      });
      clienteList.sort((a, b) =>
        a.nombres.localeCompare(b.nombres)
      );
      this.clientes = clienteList;
    });
  }

  guardarCliente() {
    this.clienteService.save(this.cliente).subscribe({
      next: (cliente: Cliente) => {
        this.messageService.add({
          severity: 'success',
          summary: '¡Correcto!',
          detail: `El cliente ${cliente.nombres} ha sido guardado correctamente`,
        });
        this.validarCliente(cliente);
      },
      error: (err) => {
        console.error(err);
        this.messageService.add({
          severity: 'error',
          summary: '¡Error!',
          detail: `${err.message}`,
        });
      },
    });
  }

  validarCliente(cliente: Cliente) {
    const index = this.clientes.findIndex(
      (e) => e.idCliente === cliente.idCliente
    );
    if (index === -1) {
      this.clientes.push(cliente);
    } else {
      this.clientes[index] = cliente;
    }
    this.formCliente.reset();
  }

  cargarClienteParaEditar() {
    if (this.selectedCliente?.idCliente == null) {
      this.messageService.add({
        severity: 'warn',
        summary: '¡¡¡Advertencia!!!',
        detail: 'No ha seleccionado ningún cliente',
      });
    } else {
      this.formCliente.patchValue(this.selectedCliente);
    }
  }

  eliminarCliente() {
    if (!this.selectedCliente?.idCliente) {
      this.messageService.add({
        severity: 'warn',
        summary: '¡¡¡Advertencia!!!',
        detail: 'No ha seleccionado ningún cliente',
      });
      return;
    }
    this.confirmationService.confirm({
      message: `¿Está seguro que desea eliminar el cliente ${this.selectedCliente.nombres}?`,
      accept: () => {
        this.clienteService
          .delete(this.selectedCliente.idCliente)
          .subscribe((cliente: Cliente) => {
            this.messageService.add({
              severity: 'info',
              summary: 'Información',
              detail: `El cliente ${cliente.nombres} ha sido eliminado correctamente`,
            });
            this.validarEliminar(cliente);
          });
      },
    });
  }

  validarEliminar(cliente: Cliente) {
    this.clientes = this.clientes.filter(
      (c) => c.idCliente !== cliente.idCliente
    );
  }

  onEliminar(cliente: Cliente) {
    this.selectedCliente = cliente;
    this.eliminarCliente();
  }

  onEditar(cliente: Cliente) {
    this.selectedCliente = cliente;
    this.cargarClienteParaEditar();
  }

  onCancelar() {
    this.formCliente.reset();
    this.selectedCliente = null;
  }

  onGuardar() {
    this.cliente = this.formCliente.value;
    this.guardarCliente();
  }
}
