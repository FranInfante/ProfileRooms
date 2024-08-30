import { Component, OnDestroy, OnInit } from '@angular/core';
import { BookingsService } from '../../../../../shared/service/bookings.service';
import { Bookings } from '../../../../../shared/interfaces/bookings';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgbdDatepickerBasic } from "../../calendar/datepicker-basic";
import { Schedule } from '../../../../../shared/interfaces/schedule';
import { RoomService } from '../../../../../shared/service/room.service';
import { ScheduleTime } from '../../../../../shared/interfaces/EnumScheduleTime';
import { BookingStatus } from '../../../../../shared/interfaces/EnumBookingStatus';
import { Room } from '../../../../../shared/interfaces/room';
import { OfficeService } from '../../../../../shared/service/office.service';
import { Office } from '../../../../../shared/interfaces/office';
import { Extra } from '../../../../../shared/interfaces/extra';
import { Subscription, SubscriptionLike } from 'rxjs';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { SharedDateService } from '../../../../../shared/service/shared-date.service';
import { formatDate } from '@angular/common';
import { User } from '../../../../../shared/interfaces/user';
import { UserService } from '../../../../../shared/service/user.service';
import { ExtraService } from '../../../../../shared/service/extra.service';
import { ExtrasComponent } from '../../extras/extras/extras.component';
import { BookingTimes } from '../../../../../shared/interfaces/BookingTimes';

@Component({
  selector: 'app-bookings',
  standalone: true,
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css'],
  imports: [ReactiveFormsModule, NgbdDatepickerBasic]
})

export class BookingsComponent implements OnInit, OnDestroy {


  Bookings: Bookings[] | undefined;
  showCalendarToggle = false;
  showCalendarId: number | null = null;
  Schedule: Schedule | undefined;
  modifyRoomTog = false;
  createRoomTog = false;
  modifyForm: FormGroup | any;
  Rooms: Room[] | undefined;
  Offices: Office[] | undefined;
  Extras: Extra[] | undefined;
  booking: Bookings | undefined;
  Users: User[] | undefined;
  Room: Room | undefined;
  BookingTimes: BookingTimes[] | undefined;
  subscriptions: SubscriptionLike[] = [];

  OfficeToUpdateExtras: Office | undefined;

  bookSubscription!: Subscription;
  dateSubscription!: Subscription;
  selectedDate!: NgbDateStruct;
  currentDate!: Date;
  date?: string;
  
  modifyBookingTog=false;
  
  ScheduleTime = Object.values(ScheduleTime);
  BookingStatus = Object.values(BookingStatus);


  constructor(
    private bookingService: BookingsService,
    private roomService: RoomService,
    private fb: FormBuilder,
    private officeService: OfficeService,
    private sharedData:SharedDateService,
    private userService:UserService,
    private extraService: ExtraService,
  ) {}
 
  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  ngOnInit(): void {
  
    this.observableInit();
    this.getAllRooms();
    this.getAllBookings();
    this.getAllOffices();
    this.getAllUsers();
    this.formBookingInit();
  }

  modifyBookingByIdToggle(bookingId: number): void {
    
    this.subscriptions.push(this.bookingService.getBookingsById(bookingId).subscribe(data=>{
    this.booking=data;
    this.date=this.formatBookingDate(this.booking!.bookingDate)
    }))

    this.observableInit();
    this.modifyBookingTog=!this.modifyBookingTog;

  }
 
  getAllUsers(){

    this.subscriptions.push(this.userService.getAllUsers().subscribe(data=>{
      this.Users = data;
    }));

  }

  modifyBookingById(bookingId: number):void{

    if (this.modifyForm.valid) {

      this.modifyForm.patchValue({
        bookingDate:this.date,
      })

      this.modifyForm.patchValue({
        status:this.booking?.status,
      })

      this.modifyForm.patchValue({
        user:this.booking?.user,
      })
      

      this.subscriptions.push(this.bookingService.updateBookings(bookingId,this.modifyForm.value).subscribe(data=>{
        this.getAllBookings();
        this.modifyForm.reset(this.modifyForm.value);
      }));
    }
  }

  getAllRooms(): void {
    this.subscriptions.push(this.roomService.getAllRooms().subscribe(data => {
      this.Rooms = data;
    }));
  }

  getAllOffices(): void {
    this.subscriptions.push(this.officeService.getOffices().subscribe(data => {
      this.Offices = data;
    }));
  }

  showCalendar(bookingId: number): void {
    this.showCalendarId = bookingId;
    this.showCalendarToggle = !this.showCalendarToggle;
  }

  deleteBookingById(idBooking: number): void {
    this.subscriptions.push(this.bookingService.deleteBookingsById(idBooking).subscribe(() => {
      this.getAllBookings();
    }));
  }

  getAllBookings(): void {
    this.subscriptions.push(this.bookingService.getAllBookings().subscribe(allBookings => {
      this.Bookings = allBookings;
    }));
  }

  formBookingInit(): void {

    this.modifyForm = this.fb.group({
      bookingDate: [],
      timeStart: [],
      timeEnd: [],
      room: [],
      extras: [[]],
      status: [],
      user:[],
    });
  }

  observableInit(){
     this.dateSubscription = this.sharedData.currentDate.subscribe(date => {
      if (date) {
        this.selectedDate = date;
        this.onDateChanged();
      }
    });
  }

  formatBookingDate(date: Date): string {
    return formatDate(date, 'yyyy-MM-dd', 'en-US');
  }

  onDateChanged() {
    const selectedDate = new Date(this.selectedDate.year, this.selectedDate.month - 1, this.selectedDate.day);
    this.date = this.formatBookingDate(selectedDate); 
  
  }

  toggleModifyBooking() {
  this.modifyBookingTog=!this.modifyBookingTog;
  }

 onChange($event: any){

  this.Room = this.modifyForm.get('room').value;
  this.Extras = this.Room?.office.extras;
  this.getAllBookings();

 }

 onChangeCalendar() {
  this.observableInit();

}

}