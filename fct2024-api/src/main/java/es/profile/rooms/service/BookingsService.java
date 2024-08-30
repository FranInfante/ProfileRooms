package es.profile.rooms.service;

import es.profile.rooms.model.dto.BookingsDto;
import es.profile.rooms.model.entities.BookingStatus;
import es.profile.rooms.util.booking.BookingTimes;
import es.profile.rooms.util.exception.BookingException;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingsService {
    List<BookingsDto> getBookingsByDate(LocalDate date);

    Optional<BookingsDto> createBooking(BookingsDto bookings);

    Optional<BookingsDto> getBookingById(Integer id);

    Optional<BookingsDto> updateBooking(Integer id, BookingsDto bookingDetails) throws BookingException;

    List<BookingsDto> findByRoomId(Integer roomId);

    List<BookingsDto> getConflictingStandbyBookings(Integer roomId, LocalDate bookingDate);

    List<BookingsDto> findAllByBookingsDate(LocalDate localDate);

    List<BookingsDto> findAllByRoomId(Integer roomId);

    List<BookingsDto> findByUserId(Integer userId);

    List<BookingsDto> getAllBookings();

    void deleteBookingsById(Integer id);

    List<BookingTimes> getBookingTimesByRoomIdAndDate(Integer roomId, LocalDate weekday);

    List<BookingsDto> getAllBookingsByDateAndStatus(LocalDate localDate, BookingStatus status);

    List<BookingsDto> getAllBookingsByStatus(BookingStatus status);

    List<BookingsDto> getAllAcceptedBookings();

    List<BookingsDto> getAllDeniedBookings();

    List<BookingsDto> getAllStandbyBookings();

    void adminDeniyingBooking(Integer bookingId) throws EntityNotFoundException;

    void bookRoom(BookingsDto bookingsDto) throws BookingException;

    void adminAcceptingBooking(Integer bookingId) throws BookingException, EntityNotFoundException;
}
