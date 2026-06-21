import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { DetalleVentaService } from 'src/app/core/services/detalle-venta.service';
import { DetalleVenta } from 'src/app/core/models/detalle-venta';
import { API_URL } from 'src/environments/environment';

describe('DetalleVentaService', () => {
  let service: DetalleVentaService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        DetalleVentaService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(DetalleVentaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all details', () => {
    const mockDetails: DetalleVenta[] = [{ idDetalleVenta: 1, cantidad: 5, precioVenta: 10.0 } as any as DetalleVenta];

    service.getAll().subscribe((details) => {
      expect(details.length).toBe(1);
      expect(details).toEqual(mockDetails);
    });

    const req = httpMock.expectOne(`${API_URL}/detalle/all`);
    expect(req.request.method).toBe('GET');
    req.flush(mockDetails);
  });
});
