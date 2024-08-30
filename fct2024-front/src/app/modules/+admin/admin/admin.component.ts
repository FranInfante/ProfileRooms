import { Component, OnInit, ElementRef, Renderer2, Output, OnDestroy } from '@angular/core';
import { UserService } from '../../../shared/service/user.service';
import { RoomService } from '../../../shared/service/room.service';
import { BookingsService } from '../../../shared/service/bookings.service';
import { ExtraService } from '../../../shared/service/extra.service';
import { User } from '../../../shared/interfaces/user';
import { Room } from '../../../shared/interfaces/room';
import { Bookings } from '../../../shared/interfaces/bookings';
import { Extra } from '../../../shared/interfaces/extra';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserComponent } from '../components/user/user/user.component';
import { RoomsComponent } from '../components/rooms/rooms/rooms.component';
import { BookingsComponent } from '../components/bookings/bookings/bookings.component';
import { ExtrasComponent } from '../components/extras/extras/extras.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [ReactiveFormsModule, UserComponent, RoomsComponent, BookingsComponent, ExtrasComponent],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent{
  
  
  userToggle = true;
  roomToggle = false;
  bookingToggle = false;
  extraToggle = false;
  
  showUser() {
    this.userToggle = !this.userToggle;
    this.roomToggle = false;
    this.extraToggle = false;
    this.bookingToggle = false;
  }

  showRoom(): void {
    this.roomToggle = !this.roomToggle;
    this.extraToggle = false;
    this.bookingToggle = false;
    this.userToggle = false;
  }

  showBooking(): void {
    this.bookingToggle = !this.bookingToggle;
    this.roomToggle = false;
    this.extraToggle = false;
    this.userToggle = false;
  }

  showExtra(): void {
    this.extraToggle = !this.extraToggle;
    this.roomToggle = false;
    this.bookingToggle = false;
    this.userToggle = false;
  }
}
