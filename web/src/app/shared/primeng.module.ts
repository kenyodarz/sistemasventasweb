import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// primeNG
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MenuModule } from 'primeng/menu';
import { PanelModule } from 'primeng/panel';
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { FileUploadModule } from 'primeng/fileupload';
import { MenubarModule } from 'primeng/menubar';
import { PaginatorModule } from 'primeng/paginator';
import { InputTextModule } from 'primeng/inputtext';

const myModules = [
  MessagesModule,
  MessageModule,
  ToastModule,
  ConfirmDialogModule,
  MenuModule,
  PanelModule,
  CardModule,
  TableModule,
  ToolbarModule,
  FileUploadModule,
  MenubarModule,
  PaginatorModule,
  InputTextModule,
];

@NgModule({
  declarations: [],
  imports: [CommonModule, myModules],
  exports: [myModules],
})
export class PrimengModule {}
