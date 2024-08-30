package es.profile.rooms.controller.impl;

import es.profile.rooms.controller.BookingsApi;
import es.profile.rooms.model.dto.BookingsDto;
import es.profile.rooms.model.entities.BookingStatus;
import es.profile.rooms.service.BookingsService;
import es.profile.rooms.util.booking.BookingTimes;
import es.profile.rooms.util.exception.BookingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookingsController implements BookingsApi {

    private final BookingsService bookingsService;

    @Override
    public ResponseEntity<BookingsDto> getBookingsById(Integer bookingId) {
        BookingsDto booking = bookingsService.getBookingById(bookingId).get();
        return ResponseEntity.ok(booking);
    }

    @Override
    public ResponseEntity<List<BookingsDto>> getAllBookings() {
        List<BookingsDto> bookings = bookingsService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @Override
    public ResponseEntity<Void> createBookings(BookingsDto bookingsDto) {
        bookingsService.createBooking(bookingsDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteBookingsById(Integer bookingId) {
        bookingsService.deleteBookingsById(bookingId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateBookings(Integer bookingId, BookingsDto bookings) {
        Optional<BookingsDto> updatedBooking;
        try {
            updatedBooking = bookingsService.updateBooking(bookingId, bookings);
        } catch (BookingException e) {
            return ResponseEntity.badRequest().build();
        }

        return updatedBooking.map(b -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<BookingsDto>> findAllByBookingDate(LocalDate bookingsDate) {
        List<BookingsDto> bookings = bookingsService.getBookingsByDate(bookingsDate);
        return ResponseEntity.ok(bookings);
    }

    @Override
    public ResponseEntity<List<BookingsDto>> findByRoomId(Integer roomId) {
        List<BookingsDto> bookings = bookingsService.findByRoomId(roomId);
        return ResponseEntity.ok(bookings);
    }

    @Override
    public ResponseEntity<List<BookingsDto>> findByUserId(Integer userId) {
        List<BookingsDto> bookings = bookingsService.findByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @Override
    public ResponseEntity<List<BookingTimes>> getBookingTimesByRoomIdAndDate(Integer roomId, LocalDate weekday) {
        List<BookingTimes> bookings = bookingsService.getBookingTimesByRoomIdAndDate(roomId, weekday);
        return ResponseEntity.ok(bookings);
    }

    @Override
    public ResponseEntity<List<BookingsDto>> getAllAcceptedBookings() {
        return ResponseEntity.ok(bookingsService.getAllAcceptedBookings());
    }

    @Override
    public ResponseEntity<List<BookingsDto>> getAllDeniedBookings() {
        return ResponseEntity.ok(bookingsService.getAllDeniedBookings());
    }

    @Override
    public ResponseEntity<List<BookingsDto>> getConflictingBookings(int roomId, LocalDate date) {
        List<BookingsDto> conflictingBookings = bookingsService.getConflictingStandbyBookings(roomId, date);
        return ResponseEntity.ok(conflictingBookings);
    }

    @Override
    public ResponseEntity<List<BookingsDto>> getAllStandByBookings() {
        return ResponseEntity.ok(bookingsService.getAllStandbyBookings());
    }

    @Override
    public ResponseEntity<Void> adminDenyBooking(Integer bookingId) {
        try {
            bookingsService.adminDeniyingBooking(bookingId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> adminAcceptBooking(Integer bookingId) {
        try {
            bookingsService.adminAcceptingBooking(bookingId);
        } catch (BookingException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<BookingsDto>> getAllBookingsByDateAndStatus(LocalDate localDate, BookingStatus status) {
        return ResponseEntity.ok(bookingsService.getAllBookingsByDateAndStatus(localDate, status));
    }
}
