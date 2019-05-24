import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { UiModule } from './ui/ui.module';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { ChatComponent } from './chat/chat.component';
import { BubbleComponent } from './bubble/bubble.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    ChatComponent,
    BubbleComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    UiModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
