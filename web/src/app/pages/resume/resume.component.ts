import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';
@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.css'],
})
export class ResumeComponent implements OnInit {
  items: MenuItem[];
  usuario: string = '';
  constructor(private token: TokenStorageService) {}

  loginOut() {
    this.token.signOut();
  }

  ngOnInit(): void {
    this.usuario = this.token.obtenerUsuario().nombres;
    this.items = [
      {
        label: this.token.obtenerUsuario().user,
      },
      {
        label: this.token.obtenerUsuario().estado,
      },
      { label: this.token.obtenerUsuario().telefono },
      { separator: true },
      {
        label: 'Cerrar Sesión',
        icon: 'pi pi-power-off',
        routerLink: ['/login'],
        command: () => this.loginOut(),
      },
    ];
  }
}
