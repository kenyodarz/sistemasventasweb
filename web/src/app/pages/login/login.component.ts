// Angular
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
//Servicios
import { MessageService } from 'primeng/api';
import { EmpleadoService } from 'src/app/core/services/empleado.service';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  loginForm: FormGroup;
  constructor(
    private empleadoService: EmpleadoService,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  onSubmit() {
    this.empleadoService.validarEmpleado(this.loginForm.value).subscribe(
      (data) => {
        this.tokenStorage.guardarToken(data.nombres);
        this.tokenStorage.guardarUsuario(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.messageService.add({
          severity: 'success',
          summary: '¡¡¡Correcto!!!',
          detail: `Bienvenido ${data.nombres}`,
        });
        this.router.navigate(['/resume']);
      },
      (err) => {
        this.errorMessage = err.error;
        this.isLoginFailed = true;
        this.messageService.add({
          severity: 'error',
          summary: 'Login failed:',
          detail: this.errorMessage,
        });
      }
    );
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: new FormControl('', Validators.required),
      password: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(3)])
      ),
    });
  }
}
