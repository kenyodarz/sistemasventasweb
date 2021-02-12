// Angular
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
// Servicios
import { ConfirmationService, MessageService } from 'primeng/api';
import { ClienteService } from 'src/app/core/services/cliente.service';
// Modelos
import { Cliente } from 'src/app/core/models/cliente';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  cliente: Cliente = new Cliente();
  selectedCliente: Cliente = null;
  formCliente: FormGroup;

  constructor(
    private clienteService: ClienteService,
    private router: Router,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  obtenerClientes() {
    this.clienteService.getAll().subscribe((array: Cliente[]) => {
      let clienteList: Cliente[] = [];
      array.forEach((element) => {
        clienteList.push(element);
      });
      this.clientes = clienteList.sort(function (a, b) {
        if (a.nombres > b.nombres) {
          return 1;
        }
        if (a.nombres < b.nombres) {
          return -1;
        }
        return 0;
      });
    });
  }

  guardarCliente() {
    this.clienteService.save(this.cliente).subscribe((cliente: Cliente) => {
      this.messageService.add({
        severity: 'success',
        summary: '¡Correcto!',
        detail: `El cliente ${cliente.nombres} ha sido guardado correctamente`,
      });
      this.validarCliente(cliente);
    }, (err)=>{
      console.log(err);
      this.messageService.add({
        severity: 'error',
        summary: '¡Error!',
        detail: `${err.message}`,
      });
    });
  }
  validarCliente(cliente: Cliente) {
    let index = this.clientes.findIndex(
      (e) => e.idCliente === cliente.idCliente
    );
    if (index != -1) {
      this.clientes[index] = cliente;
    } else {
      this.clientes.push(cliente);
    }
    this.formCliente.reset();
  }

  guardarEditarCliente(editar: Boolean) {
    if (editar) {
      if (
        this.selectedCliente != null &&
        this.selectedCliente.idCliente != null
      ) {
        this.formCliente.patchValue(this.selectedCliente);
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: '¡¡¡Advertencia!!!',
          detail: 'No ha seleccionado ningun cliente',
        });
        return;
      }
    }
  }

  eliminarCliente() {
    if (
      this.selectedCliente == null ||
      this.selectedCliente.idCliente == null
    ) {
      this.messageService.add({
        severity: 'warn',
        summary: '¡¡¡Advertencia!!!',
        detail: 'No ha seleccionado ningun cliente',
      });
      return;
    }
    this.confirmationService.confirm({
      message: `¿Está seguro que desea elminar el cliente ${this.selectedCliente.nombres}?`,
      accept: () => {
        this.clienteService
          .delete(this.selectedCliente.idCliente)
          .subscribe((cliente: Cliente) => {
            this.messageService.add({
              severity: 'info',
              summary: 'Información',
              detail: `El cliente ${cliente.nombres} a sido eliminado Correctamente`,
            });
            this.validarEliminar(cliente);
          });
      },
    });
  }
  validarEliminar(cliente: Cliente) {
    this.clientes.splice(
      this.clientes.findIndex((e) => e.idCliente === cliente.idCliente),
      1
    );
  }

  onEliminar(cliente: Cliente) {
    this.selectedCliente = cliente;
    this.eliminarCliente();
  }

  onEditar(cliente: Cliente) {
    this.selectedCliente = cliente;
    this.guardarEditarCliente(true);
  }

  onCancelar() {
    this.formCliente.reset();
    this.selectedCliente = null;
  }

  onGuardar() {
    this.cliente = this.formCliente.value;
    this.guardarCliente();
  }

  ngOnInit(): void {
    this.obtenerClientes();
    this.formCliente = this.fb.group({
      idCliente: new FormControl(),
      dni: new FormControl(null, Validators.required),
      nombres: new FormControl(null, Validators.required),
      direccion: new FormControl(null, Validators.required),
      estado: new FormControl(null, Validators.required),
    });
  }
}
