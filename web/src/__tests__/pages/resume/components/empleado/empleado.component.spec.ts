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
  let empleadoServiceMock: jasmine.SpyObj<EmpleadoService>;
  let routerMock: jasmine.SpyObj<Router>;
  let messageServiceMock: jasmine.SpyObj<MessageService>;
  let confirmationServiceMock: jasmine.SpyObj<ConfirmationService>;

  beforeEach(async () => {
    empleadoServiceMock = jasmine.createSpyObj('EmpleadoService', ['getAll', 'save', 'delete']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);
    messageServiceMock = jasmine.createSpyObj('MessageService', ['add']);
    confirmationServiceMock = jasmine.createSpyObj('ConfirmationService', ['confirm']);

    empleadoServiceMock.getAll.and.returnValue(of([]));

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
    empleadoServiceMock.getAll.and.returnValue(of(mockEmployees));

    component.obtenerEmpleados();

    expect(component.empleados.length).toBe(2);
    expect(component.empleados[0].nombres).toBe('Ana');
    expect(component.empleados[1].nombres).toBe('Zacarias');
  });

  it('should save and add new employee', () => {
    const formVal = { idEmpleado: null, dni: '333', nombres: 'Carlos', telefono: '33', estado: '1', user: 'carlos' };
    component.formEmpleado.setValue(formVal);
    
    const saved: Empleado = { idEmpleado: 3, ...formVal };
    empleadoServiceMock.save.and.returnValue(of(saved));

    component.onGuardar();

    expect(empleadoServiceMock.save).toHaveBeenCalled();
    expect(component.empleados).toContain(saved);
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'success' }));
  });

  it('should save and update existing employee', () => {
    const existing: Empleado = { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' };
    component.empleados = [existing];

    const updatedFormVal = { idEmpleado: 1, dni: '111', nombres: 'Ana Gomez', telefono: '11', estado: '1', user: 'ana' };
    component.formEmpleado.setValue(updatedFormVal);
    
    const saved: Empleado = { ...updatedFormVal };
    empleadoServiceMock.save.and.returnValue(of(saved));

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
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'warn' }));
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
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'warn' }));
  });

  it('should delete employee when confirmed', () => {
    const selected: Empleado = { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' };
    component.empleados = [selected];
    component.seledtedEmpleado = selected;

    confirmationServiceMock.confirm.and.callFake((config: Confirmation) => {
      if (config.accept) {
        config.accept();
      }
      return confirmationServiceMock;
    });
    empleadoServiceMock.delete.and.returnValue(of(selected));

    component.eliminarEmpleado();

    expect(confirmationServiceMock.confirm).toHaveBeenCalled();
    expect(empleadoServiceMock.delete).toHaveBeenCalledWith(1);
    expect(component.empleados.length).toBe(0);
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'info' }));
  });

  it('should handle onEliminar shorthand method', () => {
    const selected: Empleado = { idEmpleado: 1, dni: '111', nombres: 'Ana', telefono: '11', estado: '1', user: 'ana' };
    spyOn(component, 'eliminarEmpleado');

    component.onEliminar(selected);

    expect(component.seledtedEmpleado).toBe(selected);
    expect(component.eliminarEmpleado).toHaveBeenCalled();
  });
});
