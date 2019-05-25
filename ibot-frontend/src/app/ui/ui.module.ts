import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ScrollDispatchModule } from '@angular/cdk/scrolling';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';

@NgModule({
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatToolbarModule,
    ScrollDispatchModule,
    MatInputModule,
    MatButtonModule
  ],
  declarations: [],
  exports: [
    MatFormFieldModule,
    MatToolbarModule,
    ScrollDispatchModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class UiModule { }
