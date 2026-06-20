import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmpleadoService } from 'src/app/core/services/empleado.service';
import { Empleado } from 'src/app/core/models/empleado';
import { API_URL } from 'src/environments/environment';

describe('EmpleadoService', () => {
  let service: EmpleadoService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EmpleadoService]
    });
    service = TestBed.inject(EmpleadoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should validate employee', () => {
    const mockEmpleado: Empleado = { idEmpleado: 1, dni: '123', nombres: 'Maria', telefono: '444', estado: '1', user: 'maria1' };
    const credenciales = { username: 'maria1', password: '123' };

    service.validarEmpleado(credenciales).subscribe((empleado) => {
      expect(empleado).toEqual(mockEmpleado);
    });

    const req = httpMock.expectOne(`${API_URL}/empleados/validar/maria1/123`);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmpleado);
  });
});
