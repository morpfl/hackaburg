import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'time'
})
export class TimePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    const date = new Date(value);
    var h = date.getHours();
    var m = date.getMinutes();
    // add a zero in front of numbers<10
    m = this.timeZerofill(m);
    return h + ":" + m;
  }

  timeZerofill(i) {
    if (i < 10) {
      i = "0" + i;
    }
    return i;
  }

}
