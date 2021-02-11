import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// primeNG
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MenuModule } from 'primeng/menu';

const myModules = [
  MessagesModule,
  MessageModule,
  ToastModule,
  ConfirmDialogModule,
  MenuModule,
];

@NgModule({
  declarations: [],
  imports: [CommonModule, myModules],
  exports: [myModules],
})
export class PrimengModule {}
