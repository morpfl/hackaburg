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

  getType(): string | null {
    const type = this.cookieService.get('insuranceType');
    if (isString(type) && type.length > 0) {
      return type;
    }
    return null;
  }

  active(): boolean {
    return this.getId() !== null;
  }

  typeSet(): boolean {
    return this.getType() !== null;
  }

  setId(id: string): void {
    this.cookieService.set('conversationId', id, 1, '/');
  }

  setType(type: string): void {
    this.cookieService.set('insuranceType', type, 1, '/');
  }

  softSetType(type: string): void {
    if (!this.typeSet()) {
      this.setType(type);
    }
  }

  start(): void {
    if (!this.active()) {
      this.setId(Math.random().toString(36) + '-' + new Date().getTime().toString());
    }
  }

}
