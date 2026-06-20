import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { LoginGuard } from 'src/app/guards/login.guard';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';

describe('LoginGuard', () => {
  let guard: LoginGuard;
  let routerMock: jasmine.SpyObj<Router>;
  let tokenStorageMock: jasmine.SpyObj<TokenStorageService>;

  beforeEach(() => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const tokenStorageSpy = jasmine.createSpyObj('TokenStorageService', ['obtenerToken']);

    TestBed.configureTestingModule({
      providers: [
        LoginGuard,
        { provide: Router, useValue: routerSpy },
        { provide: TokenStorageService, useValue: tokenStorageSpy }
      ]
    });

    guard = TestBed.inject(LoginGuard);
    routerMock = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    tokenStorageMock = TestBed.inject(TokenStorageService) as jasmine.SpyObj<TokenStorageService>;
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should return true and not navigate when token exists', () => {
    tokenStorageMock.obtenerToken.and.returnValue('valid-token');

    const result = guard.canActivate();

    expect(result).toBeTrue();
    expect(routerMock.navigate).not.toHaveBeenCalled();
  });

  it('should return true and navigate to /login when token does not exist', () => {
    tokenStorageMock.obtenerToken.and.returnValue(null);

    const result = guard.canActivate();

    expect(result).toBeTrue();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });
});
