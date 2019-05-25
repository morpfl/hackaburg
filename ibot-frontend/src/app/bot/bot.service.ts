import { Injectable, EventEmitter } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { isNumber, isString } from 'util';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SessionService } from '../session/session.service';
import { Observable } from 'rxjs';
import { DialogResponse, Intent } from './dialogResponse.interface';
import { environment } from 'src/environments/environment';
import { Message } from './message.interface';
import { InsuranceService } from '../insurance/insurance.service';
import { Recommendation } from '../insurance/recommendation.interface';

@Injectable({
  providedIn: 'root'
})
export class BotService {

  results = new EventEmitter<Recommendation[]>();

  constructor(
    private http: HttpClient,
    private session: SessionService,
    private backend: InsuranceService
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
        date: new Date(),
        isGif: message.content.substr(message.content.lastIndexOf('.') + 1).toLowerCase() === 'gif'
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
    if (mlIntent === null || mlIntent === undefined || mlIntent.slug === null || mlIntent.slug === undefined) {
      return;
    }
    if (environment.insuranceTypes[mlIntent.slug] !== null &&
      environment.insuranceTypes[mlIntent.slug] !== undefined &&
      environment.insuranceTypes[mlIntent.slug].length > 0) {
      // set type
      this.session.setType(environment.insuranceTypes[mlIntent.slug]);
    }
    if (response.results === null || response.results.nlp === null || response.results.nlp.entities === null) {
      return;
    }
    // have a look at given entities
    for (const key in response.results.nlp.entities) {
      if (response.results.nlp.entities.hasOwnProperty(key)) {
        if (environment.dataFields.includes(key)) {
          const entitySet = response.results.nlp.entities[key];
          let mlEntity: any = null;
          entitySet.forEach( e1 => {
            if (mlEntity === null || e1.confidence > mlEntity.confidence) {
              mlEntity = e1;
            }
          });
          this.backend.saveInformation({
            key: key,
            value: mlEntity.data
          }).subscribe( resp => {
            if (resp.isFinished) {
              // FINISHED !!!
              this.backend.getRecommendation().subscribe( recommendations => {
                console.log(recommendations);
                this.results.emit(recommendations);
              });
            }
          });
        }
      }
    }
  }

}
