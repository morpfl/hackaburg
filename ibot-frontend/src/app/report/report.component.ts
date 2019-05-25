import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { Recommendation } from '../insurance/recommendation.interface';
import { SessionService } from '../session/session.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit, OnChanges {

  @Input() results: Recommendation[];

  showResult = false;
  insurance = 'The student insurance'
  showElements = [];
  resultList = [];
  type: string;

  constructor(
    private sessionService: SessionService,
    private appComp: AppComponent
  ) { }

  ngOnInit() {
    setTimeout(() => {
      this.showResult = true;
      setTimeout(() => {
        this.showElements.push(1);
        setTimeout(() => {
          this.showElements.push(1);
          setTimeout(() => {
            this.showElements.push(1);
            setTimeout(() => {
              this.showElements.push(1);
            }, 200);
          }, 200);
        }, 200);
      }, 200);
    }, 3000);
    //get type of inscurance
    this.type = this.sessionService.getType();
  }

  ngOnChanges() {
    if (this.results) {
      this.resultList = [[],[]];
      let e: string;
      for (let i = 0; i <= Math.min(1, this.results.length); i++) {
        if (this.type === 'Car') {
          e = this.results[i].carType;
          if (e !== undefined && e !== null) {
            if (e === 'SMALL_CAR') this.resultList[i].push('Your insurance is for small cars.');
            else if (e === 'MEDIUM_CAR') this.resultList[i].push('Your insurance is for normal cars.');
            else this.resultList[i].push('Your insurance is for big cars.');
          }
          e = this.results[i].areaType;
          if (e !== undefined && e !== null) {
            if (e === 'CITY') this.resultList[i].push('You get a car insurance for a city.');
            else this.resultList[i].push('You got a car insurance for the countryside.');
          }
          e = this.results[i].kilometers_per_year;
          if (e !== undefined && e !== null) this.resultList[i].push('You are driving ' + e + ' kilometers per year.');
        } else if (this.type === 'Home') {
          e = this.results[i].age;
          if (e !== undefined && e !== null) this.resultList[i].push('Your house is ' + e + ' years old.');
          e = this.results[i].houseType;
          if (e !== undefined && e !== null) {
            if (e === 'APARTMENT') this.resultList[i].push('You get an insurance for your apartment');
            else this.resultList[i].push('This insurance is for a free standing house.');
          }
          e = this.results[i].size;
          if (e !== undefined && e !== null) this.resultList[i].push('Your house has ' + e + ' square kilometers.');
        } else if (this.type === 'Homecontents') {
          e = this.results[i].living_space_in_sqaure_meters;
          if (e !== undefined && e !== null) this.resultList[i].push('Your insurance is for a house with a living space of ' + e + ' square kilometers.');
          e = this.results[i].insurantType;
          if (e !== undefined && e !== null) {
            if (e === 'SINGLE') this.resultList[i].push('Your insurance is for singles.');
            else this.resultList[i].push('Your insurance is for families');
          }
        } else if (this.type === 'Bike') {
          e = this.results[i].bikeType;
          if (e !== undefined && e !== null) {
            if (e === 'CITY_BIKE') this.resultList[i].push('You have an insurance for your city bike.');
            else if (e === 'MOUNTAIN_BIKE') this.resultList[i].push('You have an insurance for yout mountain bike.');
            else if (e === 'EBIKE') this.resultList[i].push('You have an insurance for your e-bike.');
            else this.resultList[i].push('You have an insurance for your road bike.');
          }
          e = this.results[i].insurantType;
          if (e !== undefined && e !== null) {
            if (e === 'STUDENT') this.resultList[i].push('You have a bike insurance for student conditions.');
            else this.resultList[i].push('You have a bike insurance for persons which are not studying.')
          }
        } else {
          e = this.results[i].liabilityType;
          if (e !== undefined && e !== null) {
            if (e === 'SINGLE') this.resultList[i].push('It\'s a liability insurance.');
            else this.resultList[i].push('It\'s a family liability insurance.');
          }
          e = this.results[i].insured_amount;
          if (e !== undefined && e !== null) {
            if (e === 'FIVE_MIO') this.resultList[i].push('The insurance covers up to 5 million euros.');
            else if (e === 'TEN_MIO') this.resultList[i].push('The insurance covers up to 10 million euros.');
            else this.resultList[i].push('The insurance covers up to 15 million euros.');
          }
        }
      }
    }
  }

  reset(): void {
    this.appComp.showResults = false;
    this.appComp.clearChat();
  }
 }
