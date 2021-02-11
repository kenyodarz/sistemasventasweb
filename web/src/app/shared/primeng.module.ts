import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

const myModules = []

@NgModule({
  declarations: [],
  imports: [CommonModule, myModules],
  exports: [myModules],
})
export class PrimengModule {}
