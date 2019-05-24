import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { isNumber, isString } from 'util';
import { HttpClient } from '@angular/common/http';
import { SessionService } from '../session/session.service';

@Injectable({
  providedIn: 'root'
})
export class BotService {

  constructor(
    private http: HttpClient,
    private session: SessionService
  ) { }

}
