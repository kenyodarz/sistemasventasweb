import { TestBed } from '@angular/core/testing';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';

describe('TokenStorageService', () => {
  let service: TokenStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TokenStorageService);
    sessionStorage.clear();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should save and get token', () => {
    service.guardarToken('test-token');
    expect(service.obtenerToken()).toBe('test-token');
  });

  it('should save and get user', () => {
    const user = { id: 1, nombres: 'Juan' };
    service.guardarUsuario(user);
    expect(service.obtenerUsuario()).toEqual(user);
  });

  it('should clear session storage on signOut', () => {
    service.guardarToken('test-token');
    service.guardarUsuario({ id: 1 });
    service.signOut();
    expect(service.obtenerToken()).toBeNull();
    expect(service.obtenerUsuario()).toBeNull();
  });
});
