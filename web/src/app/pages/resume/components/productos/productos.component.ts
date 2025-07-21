import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
  FormsModule,
  FormControl,
} from '@angular/forms';
import { Router } from '@angular/router';

// PrimeNG
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';

// Servicios
import { ProductoService } from 'src/app/core/services/producto.service';
// Modelo
import { Producto } from 'src/app/core/models/producto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    ToastModule,
    ConfirmDialogModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    InputNumberModule,
    CardModule,
  ],
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css'],
})
export class ProductosComponent implements OnInit {
  producto: Producto = new Producto();
  productos: Producto[] = [];
  selectedProducto: Producto = null;
  formProducto: FormGroup;

  constructor(
    private productoService: ProductoService,
    private router: Router,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.obtenerProductos();
    this.formProducto = this.fb.group({
      idProducto: new FormControl(),
      nombres: new FormControl(null, Validators.required),
      precio: new FormControl(0.0, Validators.required),
      stock: new FormControl(0, Validators.required),
      estado: new FormControl(
        null,
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(1),
        ])
      ),
    });
  }

  obtenerProductos(): void {
    this.productoService.getAll().subscribe((productosList: Producto[]) => {
      this.productos = productosList.sort((a, b) =>
        a.nombres.localeCompare(b.nombres)
      );
    });
  }

  guardarProducto(): void {
    this.productoService.save(this.producto).subscribe(
      (producto) => {
        this.messageService.add({
          severity: 'success',
          summary: '¡Correcto!',
          detail: `El producto ${producto.nombres} ha sido guardado correctamente`,
        });
        this.validarProducto(producto);
      },
      (err) => {
        console.error(err);
        this.messageService.add({
          severity: 'error',
          summary: '¡Error!',
          detail: `${err.message}`,
        });
      }
    );
  }

  validarProducto(producto: Producto) {
    const index = this.productos.findIndex(
      (e) => e.idProducto === producto.idProducto
    );
    if (index !== -1) {
      this.productos[index] = producto;
    } else {
      this.productos.push(producto);
    }
    this.formProducto.reset();
  }

  editarProducto() {
    if (this.selectedProducto && this.selectedProducto.idProducto != null) {
      this.formProducto.patchValue(this.selectedProducto);
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Advertencia',
        detail: 'No ha seleccionado ningún producto',
      });
    }
  }

  eliminarProducto() {
    if (!this.selectedProducto || !this.selectedProducto.idProducto) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Advertencia',
        detail: 'No ha seleccionado ningún producto',
      });
      return;
    }

    this.confirmationService.confirm({
      message: `¿Está seguro que desea eliminar el producto ${this.selectedProducto.nombres}?`,
      accept: () => {
        this.productoService
          .delete(this.selectedProducto.idProducto)
          .subscribe((producto: Producto) => {
            this.messageService.add({
              severity: 'info',
              summary: 'Información',
              detail: `El producto ${producto.nombres} ha sido eliminado correctamente`,
            });
            this.validarEliminar(producto);
          });
      },
    });
  }

  validarEliminar(producto: Producto) {
    this.productos = this.productos.filter(
      (e) => e.idProducto !== producto.idProducto
    );
  }

  onEliminar(producto: Producto) {
    this.selectedProducto = producto;
    this.eliminarProducto();
  }

  onEditar(producto: Producto) {
    this.selectedProducto = producto;
    this.editarProducto();
  }

  onCancelar() {
    this.formProducto.reset();
    this.selectedProducto = null;
  }

  onGuardar() {
    this.producto = this.formProducto.value;
    this.guardarProducto();
  }
}
