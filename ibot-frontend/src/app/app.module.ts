import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { UiModule } from './ui/ui.module';
import { ChatComponent } from './chat/chat.component';
import { BubbleComponent } from './bubble/bubble.component';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { TimePipe } from './time.pipe';

@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
    BubbleComponent,
    TimePipe
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    UiModule,
    HttpClientModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
