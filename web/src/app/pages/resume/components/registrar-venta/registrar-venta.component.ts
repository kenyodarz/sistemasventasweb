// Angular
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
//Servicios
import { ConfirmationService, MessageService } from 'primeng/api';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { ProductoService } from 'src/app/core/services/producto.service';
// Modelos
import { Cliente } from 'src/app/core/models/cliente';
import { Producto } from 'src/app/core/models/producto';

@Component({
  selector: 'app-registrar-venta',
  templateUrl: './registrar-venta.component.html',
  styleUrls: ['./registrar-venta.component.css'],
})
export class RegistrarVentaComponent implements OnInit {
  cliente: Cliente = new Cliente();
  producto: Producto = new Producto();
  dni: string = '';
  idProducto: number;
  nombreCliente = '';
  cantidarProducto: number = 0
  constructor(
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private router: Router,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  buscarClientePorDNI(dni: string) {
    this.clienteService.encontrarClientePorDNI(dni).subscribe(
      (cliente: Cliente) => {
        this.cliente = cliente;
        this.dni = cliente.dni;
        this.nombreCliente = cliente.nombres;
      },
      (err) => {
        this.messageService.add({
          severity: 'error',
          summary: '¡¡¡Error!!!',
          detail: err.error,
        });
        this.dni = '';
        this.nombreCliente = '';
      }
    );
  }

  obtenerProducto(id: number) {
    if (id !== null && id !== 0) {
      this.productoService.getOne(id).subscribe((producto) => {
        this.producto = producto;
        this.idProducto = producto.idProducto;
        if (producto.stock > 0) {
          this.cantidarProducto = 1;
        }
      }),
        (err: any) => {
          this.messageService.add({
            severity: 'error',
            summary: '¡¡¡Error!!!',
            detail: err.error,
          });
        };
    }
  }

  ngOnInit(): void {}
}
