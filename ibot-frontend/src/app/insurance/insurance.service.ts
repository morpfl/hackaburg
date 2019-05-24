import { Injectable } from '@angular/core';
import { InformationRequest } from './informationRequest.interface';
import { Observable } from 'rxjs';
import { InformationResponse } from './informationResponse.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SessionService } from '../session/session.service';

@Injectable({
  providedIn: 'root'
})
export class InsuranceService {

  constructor(
    private http: HttpClient,
    private session: SessionService
  ) { }

  saveInformation(request: InformationRequest): Observable<InformationResponse> {
    request.type = this.session.getType();
    return this.http.post<InformationResponse>(
      environment.apiBaseUrl + environment.apiInformation,
      request
    );
  }

  getRecommendation(): Observable<unknown> {
    const qParams: HttpParams = new HttpParams();
    qParams.append('type', this.session.getType());
    qParams.append('id', this.session.getId());
    return this.http.get<unknown>(
      environment.apiBaseUrl + environment.apiRecommendation,
      { params: qParams }
    );
  }

}
