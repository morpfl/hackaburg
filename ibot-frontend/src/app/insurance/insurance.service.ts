import { Injectable } from '@angular/core';
import { InformationRequest } from './informationRequest.interface';
import { Observable } from 'rxjs';
import { InformationResponse } from './informationResponse.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SessionService } from '../session/session.service';
import { Recommendation } from './recommendation.interface';

@Injectable({
  providedIn: 'root'
})
export class InsuranceService {

  constructor(
    private http: HttpClient,
    private session: SessionService
  ) { }

  saveInformation(request: InformationRequest): Observable<InformationResponse> {
    console.log(request);
    request.type = this.session.getType();
    request.id = this.session.getId();
    return this.http.post<InformationResponse>(
      environment.apiBaseUrl + environment.apiInformation,
      request
    );
  }

  getRecommendation(): Observable<Recommendation[]> {
    const qParams: HttpParams = new HttpParams();
    return this.http.get<Recommendation[]>(
      environment.apiBaseUrl + environment.apiRecommendation + '/' + this.session.getId() + '/' + this.session.getType()
    );
  }

}
