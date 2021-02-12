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
  constructor(
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private router: Router,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  

  ngOnInit(): void {}
}
