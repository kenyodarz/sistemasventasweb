// Angular
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
// Servicios
import { ConfirmationService, MessageService } from 'primeng/api';
import { ProductoService } from 'src/app/core/services/producto.service';
// Modelos
import { Producto } from 'src/app/core/models/producto';

@Component({
  selector: 'app-productos',
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

  obtenerProductos(): void {
    this.productoService.getAll().subscribe((productosList: Producto[]) => {
      let productos: Producto[] = [];
      productosList.forEach((producto) => {
        productos.push(producto);
      });
      this.productos = productos.sort(function (a, b) {
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

  guardarProducto(): void {
    this.productoService.save(this.producto).subscribe(
      (producto) => {
        this.messageService.add({
          severity: 'success',
          summary: '¡Correcto!',
          detail: `el producto ${producto.nombres} ha sido guardado correctamente`,
        });
        this.validarProducto(producto);
      },
      (err) => {
        console.log(err);
        this.messageService.add({
          severity: 'error',
          summary: '¡Error!',
          detail: `${err.message}`,
        });
      }
    );
  }
  validarProducto(producto: Producto) {
    let index = this.productos.findIndex(
      (e) => e.idProducto === producto.idProducto
    );
    if (index != -1) {
      this.productos[index] = producto;
    } else {
      this.productos.push(producto);
    }
  }

  editarProducto() {
    if (
      this.selectedProducto != null &&
      this.selectedProducto.idProducto != null
    ) {
      this.formProducto.patchValue(this.selectedProducto);
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: '¡¡¡Advertencia!!!',
        detail: 'No ha seleccionado ningun producto',
      });
    }
  }

  eliminarProducto() {
    if (
      this.selectedProducto == null &&
      this.selectedProducto.idProducto == null
    ) {
      this.messageService.add({
        severity: 'warn',
        summary: '¡¡¡Advertencia!!!',
        detail: 'No ha seleccionado ningun productos',
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
              detail: `El producto ${producto.nombres} a sido eliminado Correctamente`,
            });
            this.validarEliminar(producto);
          });
      },
    });
  }
  validarEliminar(producto: Producto) {
    this.productos.splice(
      this.productos.findIndex((e) => e.idProducto === producto.idProducto),
      1
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

  ngOnInit(): void {
    this.obtenerProductos();
    this.formProducto = this.fb.group({
      idProducto: new FormControl(),
      nombres: new FormControl(null, Validators.required),
      precio: new FormControl(0.0, Validators.required),
      stock: new FormControl(0, Validators.required),
      estado: new FormControl(null, Validators.required),
    });
  }
}
