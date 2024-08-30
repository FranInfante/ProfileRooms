import { BookingStatus } from "./EnumBookingStatus";
import { Extra } from "./extra";
import { Room } from "./room";
import { User } from "./user";


export interface Bookings {
    id: number;
    ticketTime: string;
    bookingDate: Date;
    timeStart: string;
    timeEnd: string;
    totalPrice: number;
    extras: Extra[];
    room: Room;
    user: User;
    status: BookingStatus;

}
