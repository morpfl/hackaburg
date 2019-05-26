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
        setTimeout(() => {
          this.showResults = true;
        }, 1800);
      }
    });
  }

  clearChat() {
    this.results = [];
    this.sessionService.restart();
  }

}

