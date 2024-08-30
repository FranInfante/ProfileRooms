import { Component, OnDestroy, OnInit } from '@angular/core';
import { Room } from '../../../shared/interfaces/room';
import { CommonModule } from '@angular/common';
import {
  ActivatedRoute,
  Params,
  RouterModule
} from '@angular/router';
import { RoomService } from '../../../shared/service/room.service';
import { ASSET_URLS } from '../../../shared/components/constants';
import { SubscriptionLike } from 'rxjs';

@Component({
  selector: 'app-room-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './room-detail.component.html',
  styleUrl: './room-detail.component.css',
})
export class RoomDetailComponent implements OnInit, OnDestroy {
  constructor(
    private activatedRoute: ActivatedRoute,
    private roomService: RoomService
  ) {}


  roomList: Room[] = [];
  room?: Room;
  empty = ASSET_URLS.home;
  subscriptions: SubscriptionLike[] = [];

  ngOnInit(): void {
    this.getRoomById();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  getRoomById(): void {
    const id = Number(this.activatedRoute.snapshot.params['id']);
    this.subscriptions.push(this.roomService.findRoomById(id).subscribe((data) => {
      this.room = data;
    }));
  }
}
