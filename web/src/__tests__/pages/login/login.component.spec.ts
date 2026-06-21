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
    let empleadoServiceMock: any;
    let routerMock: any;
    let tokenStorageMock: any;
    let messageServiceMock: any;

    beforeEach(async () => {
        empleadoServiceMock = {
            validarEmpleado: vi.fn().mockName("EmpleadoService.validarEmpleado")
        };
        routerMock = {
            navigate: vi.fn().mockName("Router.navigate")
        };
        tokenStorageMock = {
            guardarToken: vi.fn().mockName("TokenStorageService.guardarToken"),
            guardarUsuario: vi.fn().mockName("TokenStorageService.guardarUsuario")
        };
        messageServiceMock = {
            add: vi.fn().mockName("MessageService.add")
        };

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
        expect(component.loginForm.valid).toBe(false);
        expect(component.loginForm.value).toEqual({ username: '', password: '' });
    });

    it('should validate username is required', () => {
        const usernameCtrl = component.loginForm.get('username');
        usernameCtrl.setValue('');
        expect(usernameCtrl.hasError('required')).toBe(true);
    });

    it('should validate password minimum length', () => {
        const passwordCtrl = component.loginForm.get('password');
        passwordCtrl.setValue('12');
        expect(passwordCtrl.hasError('minlength')).toBe(true);
    });

    it('should successfully login and redirect on onSubmit success', () => {
        const mockEmpleado = { nombres: 'Juan Perez', user: 'juan1' } as any;
        empleadoServiceMock.validarEmpleado.mockReturnValue(of(mockEmpleado));

        component.loginForm.setValue({ username: 'juan1', password: '123' });
        component.onSubmit();

        expect(empleadoServiceMock.validarEmpleado).toHaveBeenCalledWith({ username: 'juan1', password: '123' });
        expect(tokenStorageMock.guardarToken).toHaveBeenCalledWith('Juan Perez');
        expect(tokenStorageMock.guardarUsuario).toHaveBeenCalledWith(mockEmpleado);
        expect(component.isLoggedIn).toBe(true);
        expect(component.isLoginFailed).toBe(false);
        expect(messageServiceMock.add).toHaveBeenCalled();
        expect(routerMock.navigate).toHaveBeenCalledWith(['/resume']);
    });

    it('should set error details and show error toast on onSubmit failure', () => {
        const mockError = { error: 'Credenciales inválidas' };
        empleadoServiceMock.validarEmpleado.mockReturnValue(throwError(() => mockError));

        component.loginForm.setValue({ username: 'juan1', password: '123' });
        component.onSubmit();

        expect(empleadoServiceMock.validarEmpleado).toHaveBeenCalled();
        expect(tokenStorageMock.guardarToken).not.toHaveBeenCalled();
        expect(component.isLoggedIn).toBe(false);
        expect(component.isLoginFailed).toBe(true);
        expect(component.errorMessage).toBe('Credenciales inválidas');
        expect(messageServiceMock.add).toHaveBeenCalled();
    });
});
