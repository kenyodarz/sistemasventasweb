import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { LoginGuard } from 'src/app/guards/login.guard';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';

describe('LoginGuard', () => {
    let guard: LoginGuard;
    let routerMock: any;
    let tokenStorageMock: any;

    beforeEach(() => {
        const routerSpy = {
            navigate: vi.fn().mockName("Router.navigate")
        };
        const tokenStorageSpy = {
            obtenerToken: vi.fn().mockName("TokenStorageService.obtenerToken")
        };

        TestBed.configureTestingModule({
            providers: [
                LoginGuard,
                { provide: Router, useValue: routerSpy },
                { provide: TokenStorageService, useValue: tokenStorageSpy }
            ]
        });

        guard = TestBed.inject(LoginGuard);
        routerMock = TestBed.inject(Router) as any;
        tokenStorageMock = TestBed.inject(TokenStorageService) as any;
    });

    it('should be created', () => {
        expect(guard).toBeTruthy();
    });

    it('should return true and not navigate when token exists', () => {
        tokenStorageMock.obtenerToken.mockReturnValue('valid-token');

        const result = guard.canActivate();

        expect(result).toBe(true);
        expect(routerMock.navigate).not.toHaveBeenCalled();
    });

    it('should return true and navigate to /login when token does not exist', () => {
        tokenStorageMock.obtenerToken.mockReturnValue(null);

        const result = guard.canActivate();

        expect(result).toBe(true);
        expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
    });
});
