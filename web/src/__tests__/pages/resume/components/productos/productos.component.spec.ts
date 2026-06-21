import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { ProductosComponent } from 'src/app/pages/resume/components/productos/productos.component';
import { ProductoService } from 'src/app/core/services/producto.service';
import { Confirmation, ConfirmationService, MessageService } from 'primeng/api';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Producto } from 'src/app/core/models/producto';

describe('ProductosComponent', () => {
    let component: ProductosComponent;
    let fixture: ComponentFixture<ProductosComponent>;
    let productoServiceMock: any;
    let routerMock: any;
    let messageServiceMock: any;
    let confirmationServiceMock: any;

    beforeEach(async () => {
        productoServiceMock = {
            getAll: vi.fn().mockName("ProductoService.getAll"),
            save: vi.fn().mockName("ProductoService.save"),
            delete: vi.fn().mockName("ProductoService.delete")
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

        productoServiceMock.getAll.mockReturnValue(of([]));

        await TestBed.configureTestingModule({
            imports: [
                ReactiveFormsModule,
                NoopAnimationsModule,
                ProductosComponent
            ],
            providers: [
                { provide: ProductoService, useValue: productoServiceMock },
                { provide: Router, useValue: routerMock },
                { provide: MessageService, useValue: messageServiceMock },
                { provide: ConfirmationService, useValue: confirmationServiceMock }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(ProductosComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should load sorted products on init', () => {
        const mockProducts: Producto[] = [
            { idProducto: 2, nombres: 'Teclado Mecanico', precio: 80.0, stock: 5, estado: '1' },
            { idProducto: 1, nombres: 'Mouse Optico', precio: 20.0, stock: 10, estado: '1' }
        ];
        productoServiceMock.getAll.mockReturnValue(of(mockProducts));

        component.obtenerProductos();

        expect(component.productos.length).toBe(2);
        expect(component.productos[0].nombres).toBe('Mouse Optico');
        expect(component.productos[1].nombres).toBe('Teclado Mecanico');
    });

    it('should save and add new product', () => {
        const formVal = { idProducto: null, nombres: 'Monitor', precio: 250.0, stock: 4, estado: '1' };
        component.formProducto.setValue(formVal);

        const saved: Producto = { idProducto: 3, ...formVal };
        productoServiceMock.save.mockReturnValue(of(saved));

        component.onGuardar();

        expect(productoServiceMock.save).toHaveBeenCalled();
        expect(component.productos).toContain(saved);
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'success' }));
    });

    it('should save and update existing product', () => {
        const existing: Producto = { idProducto: 1, nombres: 'Mouse Optico', precio: 20.0, stock: 10, estado: '1' };
        component.productos = [existing];

        const updatedFormVal = { idProducto: 1, nombres: 'Mouse Optico Inalambrico', precio: 25.0, stock: 10, estado: '1' };
        component.formProducto.setValue(updatedFormVal);

        const saved: Producto = { ...updatedFormVal };
        productoServiceMock.save.mockReturnValue(of(saved));

        component.onGuardar();

        expect(component.productos[0].nombres).toBe('Mouse Optico Inalambrico');
        expect(component.productos[0].precio).toBe(25.0);
    });

    it('should handle save error', () => {
        const formVal = { idProducto: null, nombres: 'Monitor', precio: 250.0, stock: 4, estado: '1' };
        component.formProducto.setValue(formVal);
        productoServiceMock.save.mockReturnValue(throwError(() => new Error('DB Error')));

        component.onGuardar();

        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'error' }));
    });

    it('should load product to edit form', () => {
        const selected: Producto = { idProducto: 1, nombres: 'Mouse Optico', precio: 20.0, stock: 10, estado: '1' };
        component.onEditar(selected);

        expect(component.selectedProducto).toBe(selected);
        expect(component.formProducto.value).toEqual(selected);
    });

    it('should show warning if editing product when none selected', () => {
        component.selectedProducto = null;
        component.editarProducto();
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'warn' }));
    });

    it('should cancel and reset form', () => {
        component.formProducto.patchValue({ nombres: 'Test' });
        component.selectedProducto = { idProducto: 1 } as any as Producto;
        component.onCancelar();

        expect(component.selectedProducto).toBeNull();
        expect(component.formProducto.get('nombres').value).toBeNull();
    });

    it('should show warning when deleting without selection', () => {
        component.selectedProducto = null;
        component.eliminarProducto();
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'warn' }));
    });

    it('should delete product when confirmed', () => {
        const selected: Producto = { idProducto: 1, nombres: 'Mouse Optico', precio: 20.0, stock: 10, estado: '1' };
        component.productos = [selected];
        component.selectedProducto = selected;

        confirmationServiceMock.confirm.mockImplementation((config: Confirmation) => {
            if (config.accept) {
                config.accept();
            }
            return confirmationServiceMock;
        });
        productoServiceMock.delete.mockReturnValue(of(selected));

        component.eliminarProducto();

        expect(confirmationServiceMock.confirm).toHaveBeenCalled();
        expect(productoServiceMock.delete).toHaveBeenCalledWith(1);
        expect(component.productos.length).toBe(0);
        expect(messageServiceMock.add).toHaveBeenCalledWith(expect.objectContaining({ severity: 'info' }));
    });

    it('should handle onEliminar shorthand method', () => {
        const selected: Producto = { idProducto: 1, nombres: 'Mouse Optico', precio: 20.0, stock: 10, estado: '1' };
        vi.spyOn(component, 'eliminarProducto').mockReturnValue(undefined);

        component.onEliminar(selected);

        expect(component.selectedProducto).toBe(selected);
        expect(component.eliminarProducto).toHaveBeenCalled();
    });
});
