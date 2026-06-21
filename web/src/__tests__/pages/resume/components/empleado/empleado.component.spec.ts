import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { EmpleadoComponent } from 'src/app/pages/resume/components/empleado/empleado.component';
import { EmpleadoService } from 'src/app/core/services/empleado.service';
import { Confirmation, ConfirmationService, MessageService } from 'primeng/api';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Empleado } from 'src/app/core/models/empleado';

describe('EmpleadoComponent', () => {
    let component: EmpleadoComponent;
    let fixture: ComponentFixture<EmpleadoComponent>;
    let empleadoServiceMock: any;
    let routerMock: any;
    let messageServiceMock: any;
    let confirmationServiceMock: any;

    beforeEach(async () => {
        empleadoServiceMock = {
            getAll: vi.fn().mockName("EmpleadoService.getAll"),
            save: vi.fn().mockName("EmpleadoService.save"),
            delete: vi.fn().mockName("EmpleadoService.delete")
        };
        routerMock = {
            navigate: vi.fn().mockName("Router.navigate")
        };
        messageServiceMock = {
            add: vi.fn().mockName("MessageService.add")
        };
        confirmationServiceMock = {
            confirm: vi.fn().mockName("ConfirmationService.confirm")
        };

        empleadoServiceMock.getAll.mockReturnValue(of([]));

        await TestBed.configureTestingModule({
            imports: [
                ReactiveFormsModule,
                NoopAnimationsModule,
                EmpleadoComponent
            ],
            providers: [
                { provide: EmpleadoService, useValue: empleadoServiceMock },
                { provide: Router, useValue: routerMock }
            ]
        })
            .overrideComponent(EmpleadoComponent, {
            set: {
                providers: [
                    { provide: MessageService, useValue: messageServiceMock },
                    { provide: ConfirmationService, useValue: confirmationServiceMock }
                ]
            }
        })
            .compileComponents();

        fixture = TestBed.createComponent(EmpleadoComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should load sorted employees on init', () => {
        const mockEmployees: Empleado[] = [
            { idEmpleado: 2, dni: '222', nombres: 'Zacarias', telefono: '22', estado: '1', user: 'zac' },
            { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' }
        ];
        empleadoServiceMock.getAll.mockReturnValue(of(mockEmployees));

        component.obtenerEmpleados();

        expect(component.empleados.length).toBe(2);
        expect(component.empleados[0].nombres).toBe('Ana');
        expect(component.empleados[1].nombres).toBe('Zacarias');
    });

    it('should save and add new employee', () => {
        const formVal = { idEmpleado: null, dni: '333', nombres: 'Carlos', telefono: '33', estado: '1', user: 'carlos' };
        component.formEmpleado.setValue(formVal);

        const saved: Empleado = { idEmpleado: 3, ...formVal };
        empleadoServiceMock.save.mockReturnValue(of(saved));

        component.onGuardar();

        expect(empleadoServiceMock.save).toHaveBeenCalled();
        expect(component.empleados).toContain(saved);
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'success' }));
    });

    it('should save and update existing employee', () => {
        const existing: Empleado = { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' };
        component.empleados = [existing];

        const updatedFormVal = { idEmpleado: 1, dni: '111', nombres: 'Ana Gomez', telefono: '11', estado: '1', user: 'ana' };
        component.formEmpleado.setValue(updatedFormVal);

        const saved: Empleado = { ...updatedFormVal };
        empleadoServiceMock.save.mockReturnValue(of(saved));

        component.onGuardar();

        expect(component.empleados[0].nombres).toBe('Ana Gomez');
    });

    it('should load employee to edit form', () => {
        const selected: Empleado = { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' };
        component.onEditar(selected);

        expect(component.seledtedEmpleado).toBe(selected);
        expect(component.formEmpleado.value).toEqual(selected);
    });

    it('should show warning if editing employee when none selected', () => {
        component.seledtedEmpleado = null;
        component.cargarEmpleadoParaEditar();
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'warn' }));
    });

    it('should cancel and reset form', () => {
        component.formEmpleado.patchValue({ nombres: 'Test' });
        component.seledtedEmpleado = { idEmpleado: 1 } as any as Empleado;
        component.onCancelar();

        expect(component.seledtedEmpleado).toBeNull();
        expect(component.formEmpleado.get('nombres').value).toBeNull();
    });

    it('should show warning when deleting without selection', () => {
        component.seledtedEmpleado = null;
        component.eliminarEmpleado();
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'warn' }));
    });

    it('should delete employee when confirmed', () => {
        const selected: Empleado = { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' };
        component.empleados = [selected];
        component.seledtedEmpleado = selected;

        confirmationServiceMock.confirm.mockImplementation((config: Confirmation) => {
            if (config.accept) {
                config.accept();
            }
            return confirmationServiceMock;
        });
        empleadoServiceMock.delete.mockReturnValue(of(selected));

        component.eliminarEmpleado();

        expect(confirmationServiceMock.confirm).toHaveBeenCalled();
        expect(empleadoServiceMock.delete).toHaveBeenCalledWith(1);
        expect(component.empleados.length).toBe(0);
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'info' }));
    });

    it('should handle onEliminar shorthand method', () => {
        const selected: Empleado = { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' };
        vi.spyOn(component, 'eliminarEmpleado').mockReturnValue(undefined);

        component.onEliminar(selected);

        expect(component.seledtedEmpleado).toBe(selected);
        expect(component.eliminarEmpleado).toHaveBeenCalled();
    });
});
