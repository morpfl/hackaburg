import { Component, OnInit, Input, AfterViewInit, OnChanges } from '@angular/core';
import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';

@Component({
  selector: 'app-bubble',
  templateUrl: './bubble.component.html',
  styleUrls: ['./bubble.component.scss']
})
export class BubbleComponent implements OnInit, OnChanges {

  @Input() bubble: any;
  @Input() bubbleListSize: number;
  bubbleText = 'Failed to load message...';

  constructor(private cdk: CdkVirtualScrollViewport) {
  }

  ngOnInit() {
  }

  ngOnChanges() {
    this.cdk.scrollTo({bottom: 0});
  }
}
