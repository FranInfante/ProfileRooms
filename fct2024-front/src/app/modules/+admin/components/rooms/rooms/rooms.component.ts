import { Component, OnDestroy, OnInit } from '@angular/core';
import { Room } from '../../../../../shared/interfaces/room';
import { RoomService } from '../../../../../shared/service/room.service';
import { ExtraService } from '../../../../../shared/service/extra.service';
import { Extra } from '../../../../../shared/interfaces/extra';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Office } from '../../../../../shared/interfaces/office';
import { OfficeService } from '../../../../../shared/service/office.service';
import { Schedule } from '../../../../../shared/interfaces/schedule';
import { Observable, SubscriptionLike } from 'rxjs';

@Component({
  selector: 'app-rooms',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})
export class RoomsComponent implements OnInit, OnDestroy {

  modifyRoomTog = false;
  roomForm!: FormGroup;
  createRoomTog=false;
  Rooms: Room[] | undefined;
  Room: Room | undefined;
  Extras: Extra[] | undefined;
  Offices: Office[] | undefined;
  Schedules: Schedule[] | undefined;
  subscriptions: SubscriptionLike[] = [];

  constructor(
    private roomService: RoomService,
    private extraService: ExtraService,
    private officeService: OfficeService,
    private fb: FormBuilder,
  
  ) { }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  ngOnInit(): void {
    this.initializeForm();
    this.getAllRooms();
    this.getAllExtras();
    this.getAllOffices();
  }

  initializeForm(): void {
    this.roomForm = this.fb.group({
      nameRoom: [""],
      size: [""],
      capacity: [""],
      priceHour: [""],
      description: [""],
      pictureList: [""],
      office: [],
    });
  }

  createRoomToggleButton(): void {
    this.createRoomTog=!this.createRoomTog;
    this.modifyRoomTog=false;
  }

  createRoom(): void {
    if (this.roomForm.valid) {
      this.subscriptions.push(this.roomService.createRoom(this.roomForm.value).subscribe(() => {
        this.modifyRoomTog = false;
        this.getAllRooms();
      }));
    }
  }

 
  modifyRoom(): void {
    if (!this.Room) return;
    if (this.roomForm.valid) {
      this.subscriptions.push(this.roomService.updateRoom(this.Room.id, this.roomForm.value).subscribe(() => {
        this.modifyRoomTog = false;
        this.getAllRooms();
      }));
    }
    this.getAllRooms();
  }

  modifyRoomToggleButton(): void {
    this.modifyRoomTog = !this.modifyRoomTog;
    this.createRoomTog=false;
  }

  modifyRoomToggle(roomId: number): void {
    this.getRoomById(roomId);
    this.modifyRoomTog = !this.modifyRoomTog;
    this.createRoomTog=false;
  }

  getAllRooms(): void {
    this.subscriptions.push(this.roomService.getAllRooms().subscribe(allRooms => {
      this.Rooms = allRooms;
    }));
  }

  deleteRoomById(roomId: number): void {
    this.subscriptions.push(this.roomService.deleteRoom(roomId).subscribe(() => {
      this.getAllRooms();
    }));
  }

  getAllExtras(): void {
    this.subscriptions.push(this.extraService.getAllExtras().subscribe(extras => {
      this.Extras = extras;
    }));
  }

  getAllOffices() {
    this.subscriptions.push(this.officeService.getOffices().subscribe(offices => {
      this.Offices = offices;
    }));
  }

  getRoomById(roomId: number) {
    this.subscriptions.push(this.roomService.findRoomById(roomId).subscribe(room => {
      this.Room = room;
      this.roomForm.patchValue(room);
    }));
  }
}
