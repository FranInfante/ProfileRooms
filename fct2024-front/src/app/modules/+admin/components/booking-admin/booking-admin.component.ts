// booking-admin.component.ts
import { Component, OnDestroy, OnInit } from '@angular/core';
import { BookingsService } from '../../../../shared/service/bookings.service';
import { Bookings } from '../../../../shared/interfaces/bookings';
import { Subscription, SubscriptionLike, pipe } from 'rxjs';
import { SharedDateService } from '../../../../shared/service/shared-date.service';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { NgbdDatepickerBasic } from '../calendar/datepicker-basic';
import { CommonModule, formatDate } from '@angular/common';

@Component({
  selector: 'app-booking-admin',
  standalone: true,
  templateUrl: './booking-admin.component.html',
  styleUrls: ['./booking-admin.component.css'],
  imports: [
    NgbdDatepickerBasic,
    CommonModule
  ]
})
export class BookingAdminComponent implements OnInit, OnDestroy {

  constructor(
    private bookingsService: BookingsService,
    private sharedDateService: SharedDateService,
  ) {
    this.currentDate = new Date();
    this.date = this.formatBookingDate(this.currentDate);
  }

  bookingList: Bookings[] = [];
  selectedDate!: NgbDateStruct;
  currentDate!: Date;
  date!: string;
  subscriptions: SubscriptionLike[] = [];

  ngOnInit(): void {
    this.listByDate();
    this.subscriptions.push(this.sharedDateService.currentDate.subscribe(date => {
      if (date) {
        this.selectedDate = date;
        this.onDateChanged();
        this.listByDate();
      }
    }));
  }

  listByDate() {
    this.subscriptions.push(this.bookingsService.findAllByBookingDate(this.date).subscribe(
      book => {
      this.bookingList = book;
    }));
  }

  formatBookingDate(date: Date): string {
    return formatDate(date, 'yyyy-MM-dd', 'en-US');
  }

  onDateChanged() {
    const selectedDate = new Date(this.selectedDate.year, this.selectedDate.month - 1, this.selectedDate.day);
    const formattedSelectedDate = this.formatBookingDate(selectedDate);
    this.date = formattedSelectedDate;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }
}
