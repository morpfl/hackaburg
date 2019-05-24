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

  bubbleList = [
    { isUser: true, msg: 'Hello Bot.efw feioaewf oiew ewoif ewiefp oefioew fiawe fnoiewf ew foiawef newaofneuwaipfn', date: new Date() },
    { isUser: false, msg: 'Hello User.', date: new Date() },
    { isUser: true, msg: 'Hello Bot.', date: new Date() },
    { isUser: false, msg: 'Hello User.', date: new Date() },
    { isUser: false, msg: 'Hello User.', date: new Date() },
    { isUser: true, msg: 'Hello Bot.', date: new Date() },
    { isUser: false, msg: 'Hello User.', date: new Date() },
    { isUser: true, msg: 'Hello Bot.', date: new Date() },
  ];

  constructor(
    private bot: BotService
  ) { }

  ngOnInit() {
    this.bot.initConversation();
  }
  send(e): void {
    e.preventDefault();
    const msg = e.target.querySelector('#msg').value;
    if (!validator.isEmpty(msg, {irgnore_whitespace: true})) {
      this.bot.sendMessage(msg).subscribe( resp => {
        this.bot.processIntent(resp);
      });
    }
  }
  typing(e) {
    e.preventDefault();
    const msg = e.path[0].value;
    this.showBtn = !validator.isEmpty(msg, {ignore_whitespace: true});
  }
}
