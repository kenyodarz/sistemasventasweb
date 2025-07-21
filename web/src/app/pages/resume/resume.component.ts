import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuItem } from 'primeng/api';
import { TokenStorageService } from 'src/app/core/services/token-storage.service';
import { RouterModule } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button'; // también necesario por pButton


@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MenubarModule,
    MenuModule, // ✅ Necesario para <p-menu>
    ButtonModule, // ✅ Necesario para pButton
  ],
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
    const usuario = this.token.obtenerUsuario();
    this.usuario = usuario.nombres;
    this.items = [
      { label: usuario.user },
      { label: usuario.estado },
      { label: usuario.telefono },
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
