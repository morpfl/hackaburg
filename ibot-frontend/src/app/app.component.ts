import { Component, OnInit } from '@angular/core';
import { BotService } from './bot/bot.service';
import { Recommendation } from './insurance/recommendation.interface';
import { SessionService } from './session/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  
  showResults = false;
  results: Recommendation[];

  constructor(
    private botService: BotService,
    private sessionService: SessionService
  ) {}

  ngOnInit() {
    this.botService.results.subscribe(res => {
      if (res) {
        this.results = res;
        this.showResults = true; 
      }
    });
    // TEST DATA ONLY
    this.sessionService.setType('Liability');
    this.botService.results.emit([
      {
        name: 'Versicherungsversicherung',
        premium: 200,
        liabilityType: 'FAMILY',
        insured_amount: 'TEN_MIO'
      },
      {
        name: 'Total insurance Ltd.',
        premium: 40,
        liabilityType: 'SINGLE',
        insured_amount: 'FIVE_MIO'
      }
    ])
    // TEST DATA ONLY
  }

  clearChat() {
    this.results = [];
    this.sessionService.restart();
  }

}

