import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import {
  FormsModule,
  ReactiveFormsModule,
  UntypedFormBuilder,
} from '@angular/forms';

// PrimeNG
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputNumberModule } from 'primeng/inputnumber';
import { ChipModule } from 'primeng/chip';

// Servicios
import { ClienteService } from 'src/app/core/services/cliente.service';
import { ProductoService } from 'src/app/core/services/producto.service';
import { VentaService } from 'src/app/core/services/venta.service';
import { DetalleVentaService } from 'src/app/core/services/detalle-venta.service';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';

// Modelos
import { Cliente } from 'src/app/core/models/cliente';
import { Producto } from 'src/app/core/models/producto';
import { Venta } from 'src/app/core/models/venta';
import { DetalleVenta } from 'src/app/core/models/detalle-venta';
import { Empleado } from 'src/app/core/models/empleado';
import { NuevaVenta } from 'src/app/core/models/nueva-venta';

@Component({
  selector: 'app-registrar-venta',
  standalone: true,
  templateUrl: './registrar-venta.component.html',
  styleUrls: ['./registrar-venta.component.css'],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    ToastModule,
    ConfirmDialogModule,
    InputTextModule,
    ButtonModule,
    TableModule,
    CardModule,
    InputNumberModule,
    ChipModule,
  ],
  providers: [ConfirmationService, MessageService],
})
export class RegistrarVentaComponent implements OnInit {
  cliente: Cliente = null;
  producto: Producto = new Producto();
  ventasNuevas: NuevaVenta[] = [];
  dni: string = '';
  idProducto: number;
  nombreCliente = '';
  total: number = 0;
  cantidadProducto: number = 0;
  numeroSerie: string;

  constructor(
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private ventaService: VentaService,
    private detalleVentaService: DetalleVentaService,
    private router: Router,
    private fb: UntypedFormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private token: TokenStorageService
  ) {}

  ngOnInit(): void {
    this.obtenerNumeroSerie();
  }

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
          detail: err.error.message,
        });
        this.dni = '';
        this.nombreCliente = '';
        this.cliente = null;
      }
    );
  }

  obtenerProducto(id: number) {
    if (id !== null && id !== 0) {
      this.productoService.obtenerProductoConStock(id).subscribe(
        (producto) => {
          this.producto = producto;
          this.idProducto = producto.idProducto;
          if (producto.stock > 0) {
            this.cantidadProducto = 1;
          }
        },
        (err: any) => {
          this.messageService.add({
            severity: 'error',
            summary: '¡¡¡Error!!!',
            detail: err.error,
          });
          this.idProducto = null;
          this.producto = new Producto();
        }
      );
    }
  }

  obtenerNumeroSerie() {
    this.ventaService
      .obtenerSerie()
      .subscribe((w) => (this.numeroSerie = w.numero));
  }

  onAgregarProducto() {
    let nuevaVenta: NuevaVenta = new NuevaVenta();
    nuevaVenta.id = this.producto.idProducto;
    nuevaVenta.idProducto = this.producto;
    nuevaVenta.descriptionP = this.producto.nombres;
    nuevaVenta.precio = this.producto.precio;
    nuevaVenta.cantidad = this.cantidadProducto;
    nuevaVenta.subtotal = this.producto.precio * this.cantidadProducto;
    this.ventasNuevas.push(nuevaVenta);
    this.producto = new Producto();
    this.calcularTotal();
    this.idProducto = null;
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

  onGenerarVenta() {
    let venta = new Venta();
    let empleado: Empleado = this.token.obtenerUsuario();
    venta.cliente = this.cliente;
    venta.empleado = empleado;
    venta.numeroSerie = this.numeroSerie;
    venta.monto = this.total;
    venta.estado = '1';
    this.ventaService.save(venta).subscribe((venta: Venta) => {
      this.agregarDetalleVente(venta);
      this.messageService.add({
        severity: 'success',
        summary: '¡¡¡Exito!!!',
        detail: `Guardado correctamente ${venta.numeroSerie}`,
      });
      this.router.navigateByUrl('home');
    });
  }

  agregarDetalleVente(venta: Venta) {
    this.ventasNuevas.forEach((nuevaVenta) => {
      let detalleVenta = new DetalleVenta();
      detalleVenta.cantidad = nuevaVenta.cantidad;
      detalleVenta.precioVenta = nuevaVenta.subtotal;
      detalleVenta.producto = nuevaVenta.idProducto;
      detalleVenta.venta = venta;
      this.detalleVentaService
        .save(detalleVenta)
        .subscribe((detalleVenta: DetalleVenta) => {
          this.actualizarStock(detalleVenta);
        });
    });
  }

  actualizarStock(detalleVenta: DetalleVenta) {
    this.productoService
      .actualizarStock(detalleVenta.producto.idProducto, detalleVenta.cantidad)
      .subscribe((producto: Producto) => {
        this.messageService.add({
          severity: 'info',
          summary: 'Agregado',
          detail: `Agregado correctamente ${producto.nombres}`,
        });
      });
  }
}
