import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductoService } from 'src/app/core/services/producto.service';
import { Producto } from 'src/app/core/models/producto';
import { API_URL } from 'src/environments/environment';

describe('ProductoService', () => {
  let service: ProductoService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductoService]
    });
    service = TestBed.inject(ProductoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get product with stock', () => {
    const mockProducto: Producto = { idProducto: 1, nombres: 'Teclado', precio: 50.0, stock: 10, estado: '1' };

    service.obtenerProductoConStock(1).subscribe((producto) => {
      expect(producto).toEqual(mockProducto);
    });

    const req = httpMock.expectOne(`${API_URL}/productos/with-stock/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockProducto);
  });

  it('should update stock', () => {
    const mockProducto: Producto = { idProducto: 1, nombres: 'Teclado', precio: 50.0, stock: 8, estado: '1' };

    service.actualizarStock(1, 2).subscribe((producto) => {
      expect(producto).toEqual(mockProducto);
    });

    const req = httpMock.expectOne(`${API_URL}/productos/actualizar/1/2`);
    expect(req.request.method).toBe('GET');
    req.flush(mockProducto);
  });
});
