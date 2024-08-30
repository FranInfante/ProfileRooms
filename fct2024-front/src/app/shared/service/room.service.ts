import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Room } from "../interfaces/room";
import { ROOM_ROUTES } from "../routes/room-routes";
import { Observable } from "rxjs";
import { Schedule } from "../interfaces/schedule";

@Injectable({
    providedIn: 'root'
})
export class RoomService {
    private http = inject(HttpClient);

    getAllRooms() : Observable<Room[]>{
        return this.http.get<Room[]>(ROOM_ROUTES.list());
    }

    findRoomById(id: number): Observable<Room>{
        return this.http.get<Room>(ROOM_ROUTES.get(id));
    }

    findRoomByCityName(cityName: string) : Observable<Room[]> {
        return this.http.get<Room[]>(ROOM_ROUTES.listByCity(cityName));
    }

    findRoomByPricePerHour(price: number) : Observable<Room[]> {
        return this.http.get<Room[]>(ROOM_ROUTES.listByPrice(price));
    }

    createRoom(rooms: Room){
        return this.http.post<Room>(ROOM_ROUTES.create(), rooms);
    }

    updateRoom(idRoom:number, rooms: Room){
        return this.http.put<Room>(ROOM_ROUTES.update(idRoom), rooms); 
    }

    deleteRoom(id: number){
        return this.http.delete<void>(ROOM_ROUTES.delete(id));
    }

    getScheduleByRoomId(id:number){
        return this.http.get<Schedule>(ROOM_ROUTES.getSchedule(id));
    }
}
