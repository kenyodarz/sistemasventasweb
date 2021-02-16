// Angular
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
//Servicios
import { ConfirmationService, MessageService } from 'primeng/api';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { ProductoService } from 'src/app/core/services/producto.service';
import { VentaService } from 'src/app/core/services/venta.service';
// Modelos
import { Cliente } from 'src/app/core/models/cliente';
import { Producto } from 'src/app/core/models/producto';
import { Venta } from 'src/app/core/models/venta';
import { NuevaVenta } from 'src/app/core/models/nueva-venta';

@Component({
  selector: 'app-registrar-venta',
  templateUrl: './registrar-venta.component.html',
  styleUrls: ['./registrar-venta.component.css'],
})
export class RegistrarVentaComponent implements OnInit {
  cliente: Cliente = new Cliente();
  producto: Producto = new Producto();
  ventasNuevas: NuevaVenta[] = [];
  dni: string = '';
  idProducto: number;
  nombreCliente = '';
  total: number;
  cantidadProducto: number = 0;
  numeroSerie: string;
  constructor(
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private ventaService: VentaService,
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
          this.cantidadProducto = 1;
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

  obtenerNumeroSerie() {
    this.ventaService.obtenerSerie().subscribe(w => this.numeroSerie = w.numero)
  }

  onAgregarProducto() {
    let nuevaVenta: NuevaVenta = new NuevaVenta();
    nuevaVenta.id = this.producto.idProducto;
    nuevaVenta.descriptionP = this.producto.nombres;
    nuevaVenta.precio = this.producto.precio;
    nuevaVenta.cantidad = this.cantidadProducto;
    nuevaVenta.subtotal = this.producto.precio * this.cantidadProducto;
    this.ventasNuevas.push(nuevaVenta);
    this.producto = new Producto();
    this.calcularTotal();
    this.idProducto = null;
    console.log(this.ventasNuevas);
  }

  onEliminar(ventaNueva) {
    this.ventasNuevas.splice(
      this.ventasNuevas.findIndex((e) => e.id === ventaNueva.id),
      1
    );
    this.calcularTotal();
  }

  calcularTotal() {
    this.total = 0;
    this.ventasNuevas.forEach((e) => {
      this.total += e.subtotal;
    });
  }

  ngOnInit(): void {
    this.obtenerNumeroSerie();
  }
}
