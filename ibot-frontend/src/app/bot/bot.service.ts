import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { isNumber, isString } from 'util';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SessionService } from '../session/session.service';
import { Observable } from 'rxjs';
import { DialogResponse, Intent } from './dialogResponse.interface';
import { environment } from 'src/environments/environment';
import { Message } from './message.interface';

@Injectable({
  providedIn: 'root'
})
export class BotService {

  constructor(
    private http: HttpClient,
    private session: SessionService
  ) { }

  initConversation(): void {
    if (!this.session.active()) {
      this.session.start();
    }
  }

  sendMessage(message: String): Observable<DialogResponse> {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: 'Token ' + environment.botRequestToken
    });
    return this.http.post<DialogResponse>(
      environment.botBaseUrl + environment.botDialog,
      {
        message: {
          type: 'text',
          content: message
        },
        conversation_id: this.session.getId(),
      },
      { headers: headers }
    );
  }

  getMessages(response: DialogResponse): Message[] {
    const messages: Message[] = [];
    response.results.messages.forEach( message => {
      messages.push({
        isUser: false,
        msg: message.content,
        date: new Date()
      });
    });
    return messages;
  }

  processIntent(response: DialogResponse): void {
    let mlIntent: Intent = null;
    response.results.nlp.intents.forEach( intent => {
      if (mlIntent === null || intent.confidence > mlIntent.confidence) {
        mlIntent = intent;
      }
    });
    if (environment.insuranceTypes.includes(mlIntent.slug)) {
      // set type
      this.session.setType(mlIntent.slug);
      return;
    }
    // if is not insurance type intent, have a look at given entities
    response.results.nlp.entities.forEach( entity => {
      console.log(entity);
    });
  }

}
