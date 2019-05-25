import { Component, OnInit } from '@angular/core';
import { BotService } from './bot/bot.service';
import { Recommendation } from './insurance/recommendation.interface';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  
  showResults = false;
  results: Recommendation;

  constructor(
    private botService: BotService
  ) {}

  ngOnInit() {
    this.botService.results.subscribe(res => {
      if (res) {
        this.results = res;
        this.showResults = true; 
      }
    });
  }

}

