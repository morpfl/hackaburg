import { Component, OnInit, Input, ViewChild, AfterViewInit } from '@angular/core';
import { CdkVirtualScrollViewport, ScrollDispatcher, CdkScrollable } from '@angular/cdk/scrolling';

@Component({
  selector: 'app-bubble',
  templateUrl: './bubble.component.html',
  styleUrls: ['./bubble.component.scss']
})
export class BubbleComponent implements OnInit, AfterViewInit {

  @Input() bubble: any;
  @Input() bubbleListSize: number;
  bubbleText = '...';

  constructor(private cdk: CdkVirtualScrollViewport) {
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.cdk.scrollToIndex(this.bubbleListSize - 1);
  }

}
