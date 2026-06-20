import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ResumeComponent } from 'src/app/pages/resume/resume.component';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';
import { MenubarModule } from 'primeng/menubar';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('ResumeComponent', () => {
  let component: ResumeComponent;
  let fixture: ComponentFixture<ResumeComponent>;
  let tokenStorageMock: jasmine.SpyObj<TokenStorageService>;

  beforeEach(async () => {
    tokenStorageMock = jasmine.createSpyObj('TokenStorageService', ['obtenerUsuario', 'signOut']);
    tokenStorageMock.obtenerUsuario.and.returnValue({
      user: 'admin',
      estado: 'A',
      telefono: '12345',
      nombres: 'Administrador'
    });

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        MenubarModule,
        MenuModule,
        ButtonModule,
        NoopAnimationsModule,
        ResumeComponent
      ],
      providers: [
        { provide: TokenStorageService, useValue: tokenStorageMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ResumeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize user data and menu items', () => {
    expect(component.usuario).toBe('Administrador');
    expect(component.items.length).toBe(5);
    expect(component.items[0].label).toBe('admin');
    expect(component.items[1].label).toBe('A');
    expect(component.items[2].label).toBe('12345');
  });

  it('should call signOut when logging out', () => {
    component.loginOut();
    expect(tokenStorageMock.signOut).toHaveBeenCalled();
  });

  it('should call loginOut when menu command Cerrar Sesión is triggered', () => {
    const logoutItem = component.items[4];
    expect(logoutItem.command).toBeDefined();
    logoutItem.command({ item: logoutItem });
    expect(tokenStorageMock.signOut).toHaveBeenCalled();
  });
});
