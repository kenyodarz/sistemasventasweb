import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { RegistrarVentaComponent } from 'src/app/pages/resume/components/registrar-venta/registrar-venta.component';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { ProductoService } from 'src/app/core/services/producto.service';
import { VentaService } from 'src/app/core/services/venta.service';
import { DetalleVentaService } from 'src/app/core/services/detalle-venta.service';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Cliente } from 'src/app/core/models/cliente';
import { Producto } from 'src/app/core/models/producto';
import { Venta } from 'src/app/core/models/venta';
import { DetalleVenta } from 'src/app/core/models/detalle-venta';
import { NuevaVenta } from 'src/app/core/models/nueva-venta';

describe('RegistrarVentaComponent', () => {
    let component: RegistrarVentaComponent;
    let fixture: ComponentFixture<RegistrarVentaComponent>;
    let clienteServiceMock: any;
    let productoServiceMock: any;
    let ventaServiceMock: any;
    let detalleVentaServiceMock: any;
    let tokenStorageMock: any;
    let messageServiceMock: any;
    let routerMock: any;
    let confirmationServiceMock: any;

    beforeEach(async () => {
        clienteServiceMock = {
            encontrarClientePorDNI: vi.fn().mockName("ClienteService.encontrarClientePorDNI")
        };
        productoServiceMock = {
            obtenerProductoConStock: vi.fn().mockName("ProductoService.obtenerProductoConStock"),
            actualizarStock: vi.fn().mockName("ProductoService.actualizarStock")
        };
        ventaServiceMock = {
            obtenerSerie: vi.fn().mockName("VentaService.obtenerSerie"),
            save: vi.fn().mockName("VentaService.save")
        };
        detalleVentaServiceMock = {
            save: vi.fn().mockName("DetalleVentaService.save")
        };
        tokenStorageMock = {
            obtenerUsuario: vi.fn().mockName("TokenStorageService.obtenerUsuario")
        };
        messageServiceMock = {
            add: vi.fn().mockName("MessageService.add")
        };
        routerMock = {
            navigateByUrl: vi.fn().mockName("Router.navigateByUrl")
        };
        confirmationServiceMock = {
            confirm: vi.fn().mockName("ConfirmationService.confirm")
        };

        // Default return values
        ventaServiceMock.obtenerSerie.mockReturnValue(of({ numero: '00000005' }));

        await TestBed.configureTestingModule({
            imports: [
                FormsModule,
                ReactiveFormsModule,
                NoopAnimationsModule,
                RegistrarVentaComponent
            ],
            providers: [
                { provide: ClienteService, useValue: clienteServiceMock },
                { provide: ProductoService, useValue: productoServiceMock },
                { provide: VentaService, useValue: ventaServiceMock },
                { provide: DetalleVentaService, useValue: detalleVentaServiceMock },
                { provide: TokenStorageService, useValue: tokenStorageMock },
                { provide: Router, useValue: routerMock }
            ]
        })
            .overrideComponent(RegistrarVentaComponent, {
            set: {
                providers: [
                    { provide: MessageService, useValue: messageServiceMock },
                    { provide: ConfirmationService, useValue: confirmationServiceMock }
                ]
            }
        })
            .compileComponents();

        fixture = TestBed.createComponent(RegistrarVentaComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
        expect(component.numeroSerie).toBe('00000005');
    });

    it('should search client by DNI successfully', () => {
        const mockCliente: Cliente = { idCliente: 1, dni: '12345', nombres: 'Juan', direccion: 'Av 1', estado: '1' };
        clienteServiceMock.encontrarClientePorDNI.mockReturnValue(of(mockCliente));

        component.buscarClientePorDNI('12345');

        expect(component.cliente).toEqual(mockCliente);
        expect(component.dni).toBe('12345');
        expect(component.nombreCliente).toBe('Juan');
    });

    it('should handle search client error', () => {
        clienteServiceMock.encontrarClientePorDNI.mockReturnValue(throwError(() => ({ error: { message: 'Not found' } })));

        component.buscarClientePorDNI('12345');

        expect(component.cliente).toBeNull();
        expect(component.dni).toBe('');
        expect(component.nombreCliente).toBe('');
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'error' }));
    });

    it('should load product successfully and set initial quantity to 1 when stock > 0', () => {
        const mockProducto: Producto = { idProducto: 10, nombres: 'Laptop', precio: 1000.0, stock: 5, estado: '1' };
        productoServiceMock.obtenerProductoConStock.mockReturnValue(of(mockProducto));

        component.obtenerProducto(10);

        expect(component.producto).toEqual(mockProducto);
        expect(component.idProducto).toBe(10);
        expect(component.cantidadProducto).toBe(1);
    });

    it('should not set quantity to 1 when stock is 0', () => {
        const mockProducto: Producto = { idProducto: 10, nombres: 'Laptop', precio: 1000.0, stock: 0, estado: '1' };
        productoServiceMock.obtenerProductoConStock.mockReturnValue(of(mockProducto));

        component.obtenerProducto(10);

        expect(component.producto).toEqual(mockProducto);
        expect(component.cantidadProducto).toBe(0);
    });

    it('should handle get product error', () => {
        productoServiceMock.obtenerProductoConStock.mockReturnValue(throwError(() => ({ error: 'Error load' })));

        component.obtenerProducto(10);

        expect(component.idProducto).toBeNull();
        expect(component.producto.idProducto).toBeNull();
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'error' }));
    });

    it('should add product to sales list and calculate total', () => {
        component.producto = { idProducto: 1, nombres: 'Mouse', precio: 20.0, stock: 10, estado: '1' };
        component.cantidadProducto = 2;

        component.onAgregarProducto();

        expect(component.ventasNuevas.length).toBe(1);
        expect(component.ventasNuevas[0].descriptionP).toBe('Mouse');
        expect(component.ventasNuevas[0].subtotal).toBe(40.0);
        expect(component.total).toBe(40.0);
        expect(component.idProducto).toBeNull();
    });

    it('should remove product from sales list and calculate total', () => {
        const venta1 = { id: 1, idProducto: new Producto(), descriptionP: 'Mouse', precio: 20.0, cantidad: 2, subtotal: 40.0 } as any as NuevaVenta;
        const venta2 = { id: 2, idProducto: new Producto(), descriptionP: 'Keyboard', precio: 50.0, cantidad: 1, subtotal: 50.0 } as any as NuevaVenta;
        component.ventasNuevas = [venta1, venta2];
        component.total = 90.0;

        component.onEliminar(venta1);

        expect(component.ventasNuevas.length).toBe(1);
        expect(component.ventasNuevas[0].id).toBe(2);
        expect(component.total).toBe(50.0);
    });

    it('should generate sale, save details, and update stock successfully', () => {
        const mockEmpleado = { idEmpleado: 1, nombres: 'Emp 1' };
        tokenStorageMock.obtenerUsuario.mockReturnValue(mockEmpleado);

        component.cliente = { idCliente: 1, nombres: 'Client 1' } as any as Cliente;
        component.numeroSerie = '00000005';
        component.total = 150.0;

        const mockVenta = { idVenta: 5, numeroSerie: '00000005', monto: 150.0, estado: '1' } as any as Venta;
        ventaServiceMock.save.mockReturnValue(of(mockVenta));

        const mockProducto = { idProducto: 2, nombres: 'Keyboard' } as any as Producto;
        const mockNuevaVenta = {
            id: 2,
            idProducto: mockProducto,
            descriptionP: 'Keyboard',
            precio: 150.0,
            cantidad: 1,
            subtotal: 150.0
        } as any as NuevaVenta;
        component.ventasNuevas = [mockNuevaVenta];

        const mockDetalleVenta = {
            idDetalleVenta: 10,
            cantidad: 1,
            precioVenta: 150.0,
            producto: mockProducto,
            venta: mockVenta
        } as any as DetalleVenta;
        detalleVentaServiceMock.save.mockReturnValue(of(mockDetalleVenta));
        productoServiceMock.actualizarStock.mockReturnValue(of(mockProducto));

        component.onGenerarVenta();

        expect(ventaServiceMock.save).toHaveBeenCalled();
        expect(detalleVentaServiceMock.save).toHaveBeenCalled();
        expect(productoServiceMock.actualizarStock).toHaveBeenCalledWith(2, 1);
        expect(messageServiceMock.add).toHaveBeenCalledTimes(2); // Venta success toast + stock update toast
        expect(routerMock.navigateByUrl).toHaveBeenCalledWith('home');
    });
});
