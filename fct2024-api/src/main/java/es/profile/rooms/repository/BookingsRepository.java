package es.profile.rooms.repository;

import es.profile.rooms.model.entities.BookingStatus;
import es.profile.rooms.model.entities.Bookings;
import es.profile.rooms.util.booking.BookingTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

    @Query("SELECT b FROM Bookings b WHERE b.bookingDate = :bookingDate")
    List<Bookings> findAllByBookingsDate(@Param("bookingDate") LocalDate bookingDate);

    @Query("SELECT b FROM Bookings b WHERE b.room.id = :roomId")
    List<Bookings> findByRoomId(@Param("roomId") Integer roomId);

    @Query("SELECT b FROM Bookings b WHERE b.user.id = :userId")
    List<Bookings> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT b.timeStart AS timeStart, b.timeEnd AS timeEnd FROM Bookings b WHERE b.room.id = :roomId AND b.bookingDate = :weekday")
    List<BookingTimes> findBookingTimesByRoomIdAndDate(Integer roomId, LocalDate weekday);

    @Query("SELECT b FROM Bookings b WHERE b.room.id = :roomId AND b.bookingDate = :bookingDate AND b.status = 'ACCEPTED'")
    List<Bookings> findByRoomAndBookingDateAccepted(@Param("roomId") Integer roomId, @Param("bookingDate") LocalDate bookingDate);

    @Query("SELECT b FROM Bookings b WHERE b.room.id = :roomId AND b.bookingDate = :bookingDate AND b.status = 'STANDBY'")
    List<Bookings> findByRoomAndBookingDateStandby(@Param("roomId") Integer roomId, @Param("bookingDate") LocalDate bookingDate);

    List<Bookings> findAllByBookingDate(LocalDate bookingDate);

    List<Bookings> findAllByBookingDateAndStatus(LocalDate bookingsDate, BookingStatus status);

    List<Bookings> findAllByStatus(BookingStatus status);
}