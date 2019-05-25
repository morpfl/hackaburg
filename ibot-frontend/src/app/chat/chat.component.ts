import { Component, OnInit } from '@angular/core';
import validator from 'validator';
import { BotService } from '../bot/bot.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  itemSize = 50;
  showBtn = false;
  showGIF = false;

  bubbleList = [];

  constructor(
    private botService: BotService
  ) {
    this.bubbleList.push({ isUser: false, msg: 'Hello, my name is iBot. I\'m here to help you with your insurance. Let\'s get to knwo each other a little. :)', date: new Date() });
  }

  ngOnInit() {
    this.botService.initConversation();
  }

  send(e): void {
    e.preventDefault();
    const msg = e.target.querySelector('#msg').value;
    if (!validator.isEmpty(msg, { ignore_whitespace: true })) {
      this.bubbleList.push({ isUser: true, msg: msg, date: new Date() });
      e.target.querySelector('#msg').value = '';
      if (!validator.isEmpty(msg, { irgnore_whitespace: true })) {
        this.botService.sendMessage(msg).subscribe(res => {
          if (res) {
            this.bubbleList = this.bubbleList.concat(this.botService.getMessages(res));
          }
        });
      }
    }
  }
  typing(e) {
    e.preventDefault();
    const msg = e.path[0].value;
    this.showBtn = !validator.isEmpty(msg, { ignore_whitespace: true });
  }
}

//bike, car, home, family, money