import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { isNumber, isString } from 'util';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(
    private cookieService: CookieService
  ) { }

  getId(): string | null {
    const id = this.cookieService.get('conversationId');
    if (isString(id) && id.length > 0) {
      return id;
    }
    return null;
  }

  active(): boolean {
    return this.getId() === null;
  }

  setId(id: string): void {
    this.cookieService.set('conversationId', id, 1, '/');
  }

}
