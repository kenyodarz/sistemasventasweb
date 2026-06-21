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
    let clienteServiceMock: any;
    let routerMock: any;
    let messageServiceMock: any;
    let confirmationServiceMock: any;

    beforeEach(async () => {
        clienteServiceMock = {
            getAll: vi.fn().mockName("ClienteService.getAll"),
            save: vi.fn().mockName("ClienteService.save"),
            delete: vi.fn().mockName("ClienteService.delete")
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

        clienteServiceMock.getAll.mockReturnValue(of([]));

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
        clienteServiceMock.getAll.mockReturnValue(of(mockClients));

        component.obtenerClientes();

        expect(component.clientes.length).toBe(2);
        expect(component.clientes[0].nombres).toBe('Ana');
        expect(component.clientes[1].nombres).toBe('Zacarias');
    });

    it('should save and add new client', () => {
        const clientFormVal = { idCliente: null, dni: '333', nombres: 'Carlos', direccion: 'Dir 3', estado: '1' };
        component.formCliente.setValue(clientFormVal);

        const savedClient: Cliente = { idCliente: 3, ...clientFormVal };
        clienteServiceMock.save.mockReturnValue(of(savedClient));

        component.onGuardar();

        expect(clienteServiceMock.save).toHaveBeenCalled();
        expect(component.clientes).toContain(savedClient);
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'success' }));
    });

    it('should save and update existing client', () => {
        const existing: Cliente = { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' };
        component.clientes = [existing];

        const updatedFormVal = { idCliente: 1, dni: '111', nombres: 'Ana Gomez', direccion: 'Dir 1 Mod', estado: '1' };
        component.formCliente.setValue(updatedFormVal);

        const savedClient: Cliente = { ...updatedFormVal };
        clienteServiceMock.save.mockReturnValue(of(savedClient));

        component.onGuardar();

        expect(component.clientes[0].nombres).toBe('Ana Gomez');
        expect(component.clientes[0].direccion).toBe('Dir 1 Mod');
    });

    it('should handle save error', () => {
        const clientFormVal = { idCliente: null, dni: '333', nombres: 'Carlos', direccion: 'Dir 3', estado: '1' };
        component.formCliente.setValue(clientFormVal);
        clienteServiceMock.save.mockReturnValue(throwError(() => new Error('DB Error')));

        component.onGuardar();

        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'error' }));
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
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'warn' }));
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
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'warn' }));
    });

    it('should delete client when confirmed', () => {
        const selected: Cliente = { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' };
        component.clientes = [selected];
        component.selectedCliente = selected;

        confirmationServiceMock.confirm.mockImplementation((config: Confirmation) => {
            if (config.accept) {
                config.accept();
            }
            return confirmationServiceMock;
        });
        clienteServiceMock.delete.mockReturnValue(of(selected));

        component.eliminarCliente();

        expect(confirmationServiceMock.confirm).toHaveBeenCalled();
        expect(clienteServiceMock.delete).toHaveBeenCalledWith(1);
        expect(component.clientes.length).toBe(0);
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'info' }));
    });

    it('should handle onEliminar shorthand method', () => {
        const selected: Cliente = { idCliente: 1, dni: '111', nombres: 'Ana', direccion: 'Dir 1', estado: '1' };
        vi.spyOn(component, 'eliminarCliente').mockReturnValue(undefined);

        component.onEliminar(selected);

        expect(component.selectedCliente).toBe(selected);
        expect(component.eliminarCliente).toHaveBeenCalled();
    });
});
