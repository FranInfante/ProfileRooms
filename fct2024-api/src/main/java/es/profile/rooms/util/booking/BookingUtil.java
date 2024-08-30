package es.profile.rooms.util.booking;

import es.profile.rooms.model.dto.BookingsDto;
import es.profile.rooms.model.entities.Bookings;
import es.profile.rooms.repository.BookingsRepository;
import es.profile.rooms.util.BookingsMapper;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@UtilityClass
public class BookingUtil {
    public boolean timeRangesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !end1.equals(start2) && !start1.equals(end2) && !end1.isBefore(start2) && !start1.isAfter(end2);
    }

    public int hoursInRange(LocalTime timeStart, LocalTime timeEnd) {
        return (int) Duration.between(timeStart, timeEnd).toHours();
    }

    public boolean isBookingAvailable(BookingsRepository bookingsRepository, BookingsDto newBooking) {
        List<Bookings> existingBookings = bookingsRepository.findByRoomAndBookingDateAccepted(
                newBooking.getRoom().getId(), newBooking.getBookingDate());

        return existingBookings.stream().noneMatch(existingBooking ->
                timeRangesOverlap(existingBooking.getTimeStart(), existingBooking.getTimeEnd(),
                        newBooking.getTimeStart(), newBooking.getTimeEnd()));
    }

    public boolean isBookingAvailable(BookingsRepository bookingsRepository, Bookings newBooking) {
        return isBookingAvailable(bookingsRepository, BookingsMapper.toDTO(newBooking));
    }

}
