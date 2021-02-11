import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// primeNG
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { SplitButtonModule } from 'primeng/splitbutton';

const myModules = [
  MessagesModule,
  MessageModule,
  ToastModule,
  ConfirmDialogModule,
  AvatarModule,
  AvatarGroupModule,
  SplitButtonModule,
];

@NgModule({
  declarations: [],
  imports: [CommonModule, myModules],
  exports: [myModules],
})
export class PrimengModule {}
