import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { ClientesComponent } from 'src/app/pages/resume/components/clientes/clientes.component';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { Confirmation, ConfirmationService, MessageService } from 'primeng/api';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Cliente } from 'src/app/core/models/cliente';

describe('ClientesComponent', () => {
  let component: ClientesComponent;
  let fixture: ComponentFixture<ClientesComponent>;
  let clienteServiceMock: jasmine.SpyObj<ClienteService>;
  let routerMock: jasmine.SpyObj<Router>;
  let messageServiceMock: jasmine.SpyObj<MessageService>;
  let confirmationServiceMock: jasmine.SpyObj<ConfirmationService>;

  beforeEach(async () => {
    clienteServiceMock = jasmine.createSpyObj('ClienteService', ['getAll', 'save', 'delete']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);
    messageServiceMock = jasmine.createSpyObj('MessageService', ['add']);
    confirmationServiceMock = jasmine.createSpyObj('ConfirmationService', ['confirm']);

    clienteServiceMock.getAll.and.returnValue(of([]));

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        NoopAnimationsModule,
        ClientesComponent
      ],
      providers: [
        { provide: ClienteService, useValue: clienteServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    })
    .overrideComponent(ClientesComponent, {
      set: {
        providers: [
          { provide: MessageService, useValue: messageServiceMock },
          { provide: ConfirmationService, useValue: confirmationServiceMock }
        ]
      }
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load sorted clients on init', () => {
    const mockClients: Cliente[] = [
      { idCliente: 2, dni: '222', nombres: 'Zacarias', direccion: 'Dir 2', estado: '1' },
      { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' }
    ];
    clienteServiceMock.getAll.and.returnValue(of(mockClients));

    component.obtenerClientes();

    expect(component.clientes.length).toBe(2);
    expect(component.clientes[0].nombres).toBe('Ana');
    expect(component.clientes[1].nombres).toBe('Zacarias');
  });

  it('should save and add new client', () => {
    const clientFormVal = { idCliente: null, dni: '333', nombres: 'Carlos', direccion: 'Dir 3', estado: '1' };
    component.formCliente.setValue(clientFormVal);
    
    const savedClient: Cliente = { idCliente: 3, ...clientFormVal };
    clienteServiceMock.save.and.returnValue(of(savedClient));

    component.onGuardar();

    expect(clienteServiceMock.save).toHaveBeenCalled();
    expect(component.clientes).toContain(savedClient);
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'success' }));
  });

  it('should save and update existing client', () => {
    const existing: Cliente = { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' };
    component.clientes = [existing];

    const updatedFormVal = { idCliente: 1, dni: '111', nombres: 'Ana Gomez', direccion: 'Dir 1 Mod', estado: '1' };
    component.formCliente.setValue(updatedFormVal);
    
    const savedClient: Cliente = { ...updatedFormVal };
    clienteServiceMock.save.and.returnValue(of(savedClient));

    component.onGuardar();

    expect(component.clientes[0].nombres).toBe('Ana Gomez');
    expect(component.clientes[0].direccion).toBe('Dir 1 Mod');
  });

  it('should handle save error', () => {
    const clientFormVal = { idCliente: null, dni: '333', nombres: 'Carlos', direccion: 'Dir 3', estado: '1' };
    component.formCliente.setValue(clientFormVal);
    clienteServiceMock.save.and.returnValue(throwError(() => new Error('DB Error')));

    component.onGuardar();

    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'error' }));
  });

  it('should load client to edit form', () => {
    const selected: Cliente = { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' };
    component.onEditar(selected);

    expect(component.selectedCliente).toBe(selected);
    expect(component.formCliente.value).toEqual(selected);
  });

  it('should show warning if editing client when none selected', () => {
    component.selectedCliente = null;
    component.cargarClienteParaEditar();
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'warn' }));
  });

  it('should cancel and reset form', () => {
    component.formCliente.patchValue({ nombres: 'Test' });
    component.selectedCliente = { idCliente: 1 } as any as Cliente;
    component.onCancelar();

    expect(component.selectedCliente).toBeNull();
    expect(component.formCliente.get('nombres').value).toBeNull();
  });

  it('should show warning when deleting without selection', () => {
    component.selectedCliente = null;
    component.eliminarCliente();
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'warn' }));
  });

  it('should delete client when confirmed', () => {
    const selected: Cliente = { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' };
    component.clientes = [selected];
    component.selectedCliente = selected;

    confirmationServiceMock.confirm.and.callFake((config: Confirmation) => {
      if (config.accept) {
        config.accept();
      }
      return confirmationServiceMock;
    });
    clienteServiceMock.delete.and.returnValue(of(selected));

    component.eliminarCliente();

    expect(confirmationServiceMock.confirm).toHaveBeenCalled();
    expect(clienteServiceMock.delete).toHaveBeenCalledWith(1);
    expect(component.clientes.length).toBe(0);
    expect(messageServiceMock.add).toHaveBeenCalledWith(jasmine.objectContaining({ severity: 'info' }));
  });

  it('should handle onEliminar shorthand method', () => {
    const selected: Cliente = { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' };
    spyOn(component, 'eliminarCliente');

    component.onEliminar(selected);

    expect(component.selectedCliente).toBe(selected);
    expect(component.eliminarCliente).toHaveBeenCalled();
  });
});
