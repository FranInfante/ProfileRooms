package es.profile.rooms.controller;

import es.profile.rooms.model.dto.BookingsDto;
import es.profile.rooms.model.entities.BookingStatus;
import es.profile.rooms.util.UriConstants;
import es.profile.rooms.util.booking.BookingTimes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping(UriConstants.BOOKINGS)
public interface BookingsApi {
    @GetMapping(UriConstants.BOOKING_BY_ID)
    ResponseEntity<BookingsDto> getBookingsById(@PathVariable Integer bookingId);

    @GetMapping
    ResponseEntity<List<BookingsDto>> getAllBookings();

    @PostMapping
    ResponseEntity<Void> createBookings(@RequestBody BookingsDto bookingsDto);

    @DeleteMapping(UriConstants.BOOKING_BY_ID)
    ResponseEntity<Void> deleteBookingsById(@PathVariable Integer bookingId);

    @PutMapping(UriConstants.BOOKING_BY_ID)
    ResponseEntity<Void> updateBookings(@PathVariable Integer bookingId, @RequestBody BookingsDto bookings);

    @GetMapping(UriConstants.BOOKINGS_BY_DATE)
    ResponseEntity<List<BookingsDto>> findAllByBookingDate(@PathVariable LocalDate bookingsDate);

    @GetMapping(UriConstants.BOOKINGS_BY_ROOM_ID)
    ResponseEntity<List<BookingsDto>> findByRoomId(@PathVariable Integer roomId);

    @GetMapping(UriConstants.BOOKINGS_BY_USER_ID)
    ResponseEntity<List<BookingsDto>> findByUserId(@PathVariable Integer userId);

    @GetMapping(UriConstants.BOOKINGS_ACCEPTED)
    ResponseEntity<List<BookingsDto>> getAllAcceptedBookings();

    @GetMapping(UriConstants.BOOKINGS_DENIED)
    ResponseEntity<List<BookingsDto>> getAllDeniedBookings();

    @GetMapping(UriConstants.ADMIN_BOOKINGS_CONFLICT)
    ResponseEntity<List<BookingsDto>> getConflictingBookings(@RequestParam int roomId, @RequestParam LocalDate date);

    @GetMapping(UriConstants.BOOKINGS_STANDBY)
    ResponseEntity<List<BookingsDto>> getAllStandByBookings();

    @PutMapping(UriConstants.ADMIN_DENY_BOOKING)
    ResponseEntity<Void> adminDenyBooking(@PathVariable Integer bookingId);

    @PutMapping(UriConstants.ADMIN_ACCEPT_BOOKING)
    ResponseEntity<Void> adminAcceptBooking(@PathVariable Integer bookingId);

    @GetMapping(UriConstants.ADMIN_BOOKINGS_BY_DATE_STATUS)
    ResponseEntity<List<BookingsDto>> getAllBookingsByDateAndStatus(@RequestParam LocalDate date, @RequestParam BookingStatus status);

    @GetMapping(UriConstants.BOOKINGS_BY_ROOM_ID_TIMES)
    ResponseEntity<List<BookingTimes>> getBookingTimesByRoomIdAndDate(@PathVariable Integer roomId, @RequestParam LocalDate weekday);
}