// datepicker-basic.component.ts
import { Component, inject, ViewEncapsulation } from '@angular/core';
import { NgbCalendar, NgbDatepickerModule, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { SharedDateService } from '../../../../shared/service/shared-date.service';

@Component({
  selector: 'ngbd-datepicker-basic',
  standalone: true,
  imports: [NgbDatepickerModule, FormsModule, JsonPipe],
  templateUrl: './datepicker-basic.html',
  styleUrl: './datepicker-basic.css',
  encapsulation: ViewEncapsulation.None,
})
export class NgbdDatepickerBasic {
  today = inject(NgbCalendar).getToday();
  model!: NgbDateStruct;
  date!: { year: number; month: number; };

  constructor(private sharedDateService: SharedDateService) {}

  onDateChange() {
   
      this.sharedDateService.changeDate(this.model);
    }
}

