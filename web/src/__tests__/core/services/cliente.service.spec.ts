import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { Cliente } from 'src/app/core/models/cliente';
import { API_URL } from 'src/environments/environment';

describe('ClienteService', () => {
  let service: ClienteService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ClienteService]
    });
    service = TestBed.inject(ClienteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all clients', () => {
    const mockClientes: Cliente[] = [{ idCliente: 1, dni: '123', nombres: 'Juan', direccion: 'Av 1', estado: '1' }];

    service.getAll().subscribe((clientes) => {
      expect(clientes.length).toBe(1);
      expect(clientes).toEqual(mockClientes);
    });

    const req = httpMock.expectOne(`${API_URL}/clientes/all`);
    expect(req.request.method).toBe('GET');
    req.flush(mockClientes);
  });

  it('should get one client', () => {
    const mockCliente: Cliente = { idCliente: 1, dni: '123', nombres: 'Juan', direccion: 'Av 1', estado: '1' };

    service.getOne(1).subscribe((cliente) => {
      expect(cliente).toEqual(mockCliente);
    });

    const req = httpMock.expectOne(`${API_URL}/clientes/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCliente);
  });

  it('should save a client', () => {
    const mockCliente: Cliente = { idCliente: 1, dni: '123', nombres: 'Juan', direccion: 'Av 1', estado: '1' };

    service.save(mockCliente).subscribe((cliente) => {
      expect(cliente).toEqual(mockCliente);
    });

    const req = httpMock.expectOne(`${API_URL}/clientes/save`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.body).toBe(JSON.stringify(mockCliente));
    req.flush(mockCliente);
  });

  it('should delete a client', () => {
    const mockCliente: Cliente = { idCliente: 1, dni: '123', nombres: 'Juan', direccion: 'Av 1', estado: '1' };

    service.delete(1).subscribe((cliente) => {
      expect(cliente).toEqual(mockCliente);
    });

    const req = httpMock.expectOne(`${API_URL}/clientes/delete/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCliente);
  });

  it('should find client by DNI', () => {
    const mockCliente: Cliente = { idCliente: 1, dni: '123', nombres: 'Juan', direccion: 'Av 1', estado: '1' };

    service.encontrarClientePorDNI('123').subscribe((cliente) => {
      expect(cliente).toEqual(mockCliente);
    });

    const req = httpMock.expectOne(`${API_URL}/clientes/dni/123`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCliente);
  });
});
