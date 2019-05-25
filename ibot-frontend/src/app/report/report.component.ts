import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  showLoader = false;
  showResult = false;
  insurance = 'The student insurance'

  constructor() { }

  ngOnInit() {
    setTimeout(() => {
      this.showLoader = true;
      setTimeout(() => {
        this.showLoader = false;
        this.showResult = true;
      }, 1800);
    }, 3000);
  }

}
