import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bookings } from '../interfaces/bookings';
import { BookingTimes } from '../interfaces/BookingTimes';
import { BOOKINGS_ROUTES } from '../routes/booking-routes';

@Injectable({
  providedIn: 'root'
})
export class BookingsService {

  constructor(private http: HttpClient) { }

  getBookingsById(bookingsId: number): Observable<Bookings> {
    return this.http.get<Bookings>(BOOKINGS_ROUTES.get(bookingsId));
  }

  getAllBookings(): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(BOOKINGS_ROUTES.list());
  }

  createBookings(bookings: Bookings): Observable<void> {
    return this.http.post<void>(BOOKINGS_ROUTES.create(), bookings);
  }

  deleteBookingsById(bookingsId: number): Observable<void> {
    return this.http.delete<void>(BOOKINGS_ROUTES.delete(bookingsId));
  }

  updateBookings(bookingsId: number, bookings: Bookings): Observable<void> {
    return this.http.put<void>(BOOKINGS_ROUTES.update(bookingsId), bookings);
  }

  findAllByBookingDate(bookingsDate: string): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(BOOKINGS_ROUTES.findByBookingDate(bookingsDate));
  }

  findByRoomId(roomId: number): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(BOOKINGS_ROUTES.findByRoomId(roomId));
  }

  findByUserId(userId: number): Observable<Bookings[]> {
    return this.http.get<Bookings[]>(BOOKINGS_ROUTES.findByUserId(userId));
  }

  getBookingTimesByRoomIdAndDate(roomId: number, date: Date): Observable<BookingTimes[]> {
    let params = new HttpParams().set('weekday', date.toString());
    return this.http.get<BookingTimes[]>(BOOKINGS_ROUTES.findHours(roomId), {params});
  }

}
