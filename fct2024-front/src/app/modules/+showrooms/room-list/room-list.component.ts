import { Component, OnDestroy, OnInit } from '@angular/core';
import { Room } from '../../../shared/interfaces/room';
import { RoomService } from '../../../shared/service/room.service';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { OfficeService } from '../../../shared/service/office.service';
import { Office } from '../../../shared/interfaces/office';
import { RoomDetailComponent } from '../room-detail/room-detail.component';
import { ASSET_URLS } from '../../../shared/components/constants';
import { SubscriptionLike } from 'rxjs';

@Component({
  selector: 'app-room-list',
  standalone: true,
  imports: [RouterLink, RoomDetailComponent, RouterOutlet, RouterModule],
  templateUrl: './room-list.component.html',
  styleUrl: './room-list.component.css',
})
export class RoomListComponent implements OnInit, OnDestroy {
  rooms: Room[] = [];
  offices: Office[] = [];
  uniquePrices: number[] = [];
  roomDetail = ASSET_URLS.roomDetail;
  subscriptions: SubscriptionLike[] = [];

  constructor(
    private roomService: RoomService,
    private officeService: OfficeService
  ) { }

  ngOnInit(): void {
    this.getOffices();
    this.getRooms();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(
      (subscription) => subscription.unsubscribe());
  }

  getUniquePrices(rooms: Room[]): number[] {
    const uniquePrices = new Set<number>();
    rooms.forEach((room) => uniquePrices.add(room.priceHour));
    return Array.from(uniquePrices);
  }

  searchByCity(term: string): void {
    const searchText = term;
    if (searchText) {
      this.subscriptions.push(this.roomService
        .findRoomByCityName(searchText)
        .subscribe((roomsByCity) => {
          this.rooms = roomsByCity;
        }));
    } else {
      this.subscriptions.push(this.roomService.getAllRooms().subscribe(
        (rooms) => (this.rooms = rooms)
      ));
    }
  }

  searchByPriceHour(price: string) {
    const priceCost = Number(price);

    if (priceCost) {
      this.subscriptions.push(this.roomService
        .findRoomByPricePerHour(priceCost)
        .subscribe((roomsByCity) => {
          this.rooms = roomsByCity;
        }));
    } else {
      this.subscriptions.push(this.roomService.getAllRooms().subscribe(
        (rooms) => (this.rooms = rooms)
      ));
    }
  }

  getOffices(): void {
    this.subscriptions.push(this.officeService
      .getOffices()
      .subscribe((offices) => (this.offices = offices)));
  }

  getRooms(): void {
    this.subscriptions.push(this.roomService.getAllRooms().subscribe(
      (rooms) => {
        this.rooms = rooms;
        this.uniquePrices = this.getUniquePrices(rooms);
      },
    ));
  }
}
