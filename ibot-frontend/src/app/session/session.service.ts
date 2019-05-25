import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { isNumber, isString } from 'util';
import { InsuranceService } from '../insurance/insurance.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(
    private cookieService: CookieService,
    private http: HttpClient
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
      this.getNewId().subscribe( id => {
        this.setId(id.toString());
      });
    }
  }

  getNewId(): Observable<number> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<number>(
      environment.apiBaseUrl + environment.apiStart, {}, httpOptions
    );
  }

}
