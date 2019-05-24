import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatToolbarModule } from '@angular/material/toolbar';

@NgModule({
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatToolbarModule
  ],
  declarations: [],
  exports: [
    MatFormFieldModule,
    MatToolbarModule
  ]
})
export class UiModule { }
