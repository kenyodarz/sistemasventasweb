import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
  ReactiveFormsModule,
  FormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

/* PrimeNG */
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';

/* Servicios */
import { EmpleadoService } from 'src/app/core/services/empleado.service';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ToastModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  loginForm!: FormGroup;

  constructor(
    private empleadoService: EmpleadoService,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: new FormControl('', Validators.required),
      password: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(3)])
      ),
    });
  }

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
}
