import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VentaService } from 'src/app/core/services/venta.service';
import { API_URL } from 'src/environments/environment';

describe('VentaService', () => {
  let service: VentaService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [VentaService]
    });
    service = TestBed.inject(VentaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should obtain serial number info', () => {
    const mockSerie = { dato: 1, numero: '00000001' };

    service.obtenerSerie().subscribe((serie) => {
      expect(serie).toEqual(mockSerie);
    });

    const req = httpMock.expectOne(`${API_URL}/ventas/serie`);
    expect(req.request.method).toBe('GET');
    req.flush(mockSerie);
  });
});
