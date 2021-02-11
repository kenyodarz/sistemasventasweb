// Angular
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
//Servicios
import { MessageService } from 'primeng/api';
import { EmpleadoService } from 'src/app/core/services/empleado.service';
// Modelos
import { Empleado } from 'src/app/core/models/empleado';

@Component({
  selector: 'app-empleado',
  templateUrl: './empleado.component.html',
  styleUrls: ['./empleado.component.css'],
})
export class EmpleadoComponent implements OnInit {
  empleado: Empleado = new Empleado();
  seledtedEmpleado: Empleado = new Empleado();
  empleados: Empleado[] = [];
  formEmpleado: FormGroup | undefined;
  constructor(
    private empleadoService: EmpleadoService,
    private router: Router,
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  obtenerEmpleados() {
    this.empleadoService.getAll().subscribe((array: Empleado[]) => {
      let empleadoList: Empleado[] = [];
      array.forEach((element) => {
        empleadoList.push(element)
      });
      this.empleados = empleadoList.sort(function (a, b) {
        if (a.nombres > b.nombres) {
          return 1
        }
        if (a.nombres < b.nombres) {
          return -1
        }
        return 0
      })
    });
  }

  ngOnInit(): void {
    this.obtenerEmpleados();
    this.formEmpleado = this.fb.group({
      idEmpleado: new FormControl(),
      dni: new FormControl(null, Validators.required),
      nombres: new FormControl(null, Validators.required),
      telefono: new FormControl(),
      estado: new FormControl(),
      user: new FormControl(Validators.required),
    });
  }
}
