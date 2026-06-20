import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { LoginComponent } from 'src/app/pages/login/login.component';
import { EmpleadoService } from 'src/app/core/services/empleado.service';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';
import { MessageService } from 'primeng/api';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let empleadoServiceMock: jasmine.SpyObj<EmpleadoService>;
  let routerMock: jasmine.SpyObj<Router>;
  let tokenStorageMock: jasmine.SpyObj<TokenStorageService>;
  let messageServiceMock: jasmine.SpyObj<MessageService>;

  beforeEach(async () => {
    empleadoServiceMock = jasmine.createSpyObj('EmpleadoService', ['validarEmpleado']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);
    tokenStorageMock = jasmine.createSpyObj('TokenStorageService', ['guardarToken', 'guardarUsuario']);
    messageServiceMock = jasmine.createSpyObj('MessageService', ['add']);

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        NoopAnimationsModule,
        LoginComponent
      ],
      providers: [
        { provide: EmpleadoService, useValue: empleadoServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: TokenStorageService, useValue: tokenStorageMock },
        { provide: MessageService, useValue: messageServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with empty values and invalid status', () => {
    expect(component.loginForm).toBeDefined();
    expect(component.loginForm.valid).toBeFalse();
    expect(component.loginForm.value).toEqual({ username: '', password: '' });
  });

  it('should validate username is required', () => {
    const usernameCtrl = component.loginForm.get('username');
    usernameCtrl.setValue('');
    expect(usernameCtrl.hasError('required')).toBeTrue();
  });

  it('should validate password minimum length', () => {
    const passwordCtrl = component.loginForm.get('password');
    passwordCtrl.setValue('12');
    expect(passwordCtrl.hasError('minlength')).toBeTrue();
  });

  it('should successfully login and redirect on onSubmit success', () => {
    const mockEmpleado = { nombres: 'Juan Perez', user: 'juan1' } as any;
    empleadoServiceMock.validarEmpleado.and.returnValue(of(mockEmpleado));

    component.loginForm.setValue({ username: 'juan1', password: '123' });
    component.onSubmit();

    expect(empleadoServiceMock.validarEmpleado).toHaveBeenCalledWith({ username: 'juan1', password: '123' });
    expect(tokenStorageMock.guardarToken).toHaveBeenCalledWith('Juan Perez');
    expect(tokenStorageMock.guardarUsuario).toHaveBeenCalledWith(mockEmpleado);
    expect(component.isLoggedIn).toBeTrue();
    expect(component.isLoginFailed).toBeFalse();
    expect(messageServiceMock.add).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/resume']);
  });

  it('should set error details and show error toast on onSubmit failure', () => {
    const mockError = { error: 'Credenciales inválidas' };
    empleadoServiceMock.validarEmpleado.and.returnValue(throwError(() => mockError));

    component.loginForm.setValue({ username: 'juan1', password: '123' });
    component.onSubmit();

    expect(empleadoServiceMock.validarEmpleado).toHaveBeenCalled();
    expect(tokenStorageMock.guardarToken).not.toHaveBeenCalled();
    expect(component.isLoggedIn).toBeFalse();
    expect(component.isLoginFailed).toBeTrue();
    expect(component.errorMessage).toBe('Credenciales inválidas');
    expect(messageServiceMock.add).toHaveBeenCalled();
  });
});
