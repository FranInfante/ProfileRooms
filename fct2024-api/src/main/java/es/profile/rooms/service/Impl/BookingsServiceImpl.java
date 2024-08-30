package es.profile.rooms.service.Impl;

import es.profile.rooms.model.dto.BookingsDto;
import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.model.entities.BookingStatus;
import es.profile.rooms.model.entities.Bookings;
import es.profile.rooms.model.entities.Extras;
import es.profile.rooms.repository.BookingsRepository;
import es.profile.rooms.repository.ExtrasRepository;
import es.profile.rooms.repository.RoomsRepository;
import es.profile.rooms.repository.UsersRepository;
import es.profile.rooms.service.BookingsService;
import es.profile.rooms.util.*;
import es.profile.rooms.util.booking.BookingTimes;
import es.profile.rooms.util.booking.BookingUtil;
import es.profile.rooms.util.exception.BookingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingsServiceImpl implements BookingsService {

    private final BookingsRepository bookingsRepository;
    private final UsersRepository usersRepository;
    private final RoomsRepository roomsRepository;
    private final ExtrasRepository extrasRepository;


    @Override
    public List<BookingsDto> getBookingsByDate(LocalDate date) {
        return BookingsMapper.toDTOList(bookingsRepository.findAllByBookingDate(date));
    }

    @Override
    public Optional<BookingsDto> createBooking(BookingsDto bookings) {
        bookings.setStatus(BookingStatus.STANDBY);
        Bookings savedBooking = bookingsRepository.save(BookingsMapper.fromDTO(bookings));
        return Optional.of(BookingsMapper.toDTO(savedBooking));
    }

    @Override
    public Optional<BookingsDto> getBookingById(Integer id) {
        return bookingsRepository.findById(id).map(BookingsMapper::toDTO).or(() -> {
            throw new EntityNotFoundException(MessageConstants.BOOKING_NOT_FOUND);
        });
    }


    @Override
    public Optional<BookingsDto> updateBooking(Integer id, BookingsDto bookingDetails) throws BookingException {
        Optional<Bookings> optionalBooking = bookingsRepository.findById(id);

        if (optionalBooking.isEmpty()) {
            return Optional.empty();
        }

        Bookings updatedBooking = BookingsMapper.fromDTO(bookingDetails);

        updatedBooking.setBookingDate(bookingDetails.getBookingDate());
        updatedBooking.setTimeStart(bookingDetails.getTimeStart());
        updatedBooking.setTimeEnd(bookingDetails.getTimeEnd());
        updatedBooking.setId(id);


        if (!BookingUtil.isBookingAvailable(bookingsRepository, updatedBooking)) {
            throw BookingException.bookingTimeConflict();
        }

        updatedBooking.setTicketTime(bookingDetails.getTicketTime());
        updatedBooking.setTotalPrice(bookingDetails.getTotalPrice());

        Float totalPrice = updatedBooking.getExtras().stream()
                .map(Extras::getPriceHour)
                .reduce(0.0f, Float::sum) +
                (updatedBooking.getRoom().getPriceHour()
                        * BookingUtil.hoursInRange(updatedBooking.getTimeStart(), updatedBooking.getTimeEnd()));

        updatedBooking.setTotalPrice(totalPrice);
        updatedBooking.setStatus(BookingStatus.STANDBY);

        if (Objects.nonNull(updatedBooking.getExtras())) {
            extrasRepository.saveAll(updatedBooking.getExtras());
            updatedBooking.setExtras(updatedBooking.getExtras());
        }

        return Optional.of(BookingsMapper.toDTO(bookingsRepository.save(updatedBooking)));
    }

    @Override
    public List<BookingsDto> findByRoomId(Integer roomId) {
        return BookingsMapper.toDTOList(bookingsRepository.findByRoomId(roomId));
    }

    @Override
    public List<BookingsDto> getAllBookings() {
        return BookingsMapper.toDTOList(bookingsRepository.findAll());
    }

    @Override
    public void deleteBookingsById(Integer id) {
        bookingsRepository.deleteById(id);
    }

    @Override
    public List<BookingTimes> getBookingTimesByRoomIdAndDate(Integer roomId, LocalDate bookingDate) {
        return bookingsRepository.findBookingTimesByRoomIdAndDate(roomId, bookingDate);
    }

    @Override
    public void bookRoom(BookingsDto bookingsDto) throws BookingException {
        if (!BookingUtil.isBookingAvailable(bookingsRepository, bookingsDto)) {
            throw BookingException.bookingTimeConflict();
        }

        Float totalPrice = bookingsDto.getExtras().stream()
                .map(ExtrasDto::getPriceHour)
                .reduce(0.0f, Float::sum) +
                (bookingsDto.getRoom().getPriceHour()
                        * BookingUtil.hoursInRange(bookingsDto.getTimeStart(), bookingsDto.getTimeEnd()));

        bookingsDto.setStatus(BookingStatus.STANDBY);
        bookingsDto.setTicketTime(LocalDateTime.now());
        bookingsDto.setTotalPrice(totalPrice);

        bookingsRepository.save(BookingsMapper.fromDTO(bookingsDto));
    }

    @Override
    public void adminAcceptingBooking(Integer bookingId) throws BookingException, EntityNotFoundException {
        Bookings bookings = bookingsRepository.findById(bookingId).orElseThrow(BookingException::bookingNotFound);
        if (!BookingUtil.isBookingAvailable(bookingsRepository, bookings)) {
            throw BookingException.bookingTimeConflict();
        }
        bookings.setStatus(BookingStatus.ACCEPTED);
        bookingsRepository.save(bookings);
    }

    @Override
    public void adminDeniyingBooking(Integer bookingId) {
        Bookings bookings = bookingsRepository.findById(bookingId).orElseThrow(BookingException::bookingNotFound);
        bookings.setStatus(BookingStatus.DENIED);
        bookingsRepository.save(bookings);
    }

    @Override
    public List<BookingsDto> getConflictingStandbyBookings(Integer roomId, LocalDate bookingDate) {
        List<Bookings> standbyBookings = bookingsRepository.findByRoomAndBookingDateStandby(roomId, bookingDate);
        List<Bookings> acceptedBookings = bookingsRepository.findByRoomAndBookingDateAccepted(roomId, bookingDate);

        return standbyBookings.stream()
                .filter(standbyBooking -> acceptedBookings.stream()
                        .anyMatch(acceptedBooking -> BookingUtil.timeRangesOverlap(
                                acceptedBooking.getTimeStart(),
                                acceptedBooking.getTimeEnd(),
                                standbyBooking.getTimeStart(),
                                standbyBooking.getTimeEnd())))
                .map(BookingsMapper::toDTO)
                .toList();
    }

    @Override
    public List<BookingsDto> findAllByBookingsDate(LocalDate localDate) {
        return BookingsMapper.toDTOList(bookingsRepository.findAllByBookingsDate(localDate));
    }

    @Override
    public List<BookingsDto> findAllByRoomId(Integer roomId) {
        return BookingsMapper.toDTOList(bookingsRepository.findByRoomId(roomId));
    }

    @Override
    public List<BookingsDto> findByUserId(Integer userId) {
        return BookingsMapper.toDTOList(bookingsRepository.findByUserId(userId));
    }

    @Override
    public List<BookingsDto> getAllBookingsByDateAndStatus(LocalDate localDate, BookingStatus status) {
        return BookingsMapper.toDTOList(bookingsRepository.findAllByBookingDateAndStatus(localDate, status));
    }

    @Override
    public List<BookingsDto> getAllBookingsByStatus(BookingStatus status) {
        return BookingsMapper.toDTOList(bookingsRepository.findAllByStatus(status));
    }

    @Override
    public List<BookingsDto> getAllAcceptedBookings() {
        return getAllBookingsByStatus(BookingStatus.ACCEPTED);
    }

    @Override
    public List<BookingsDto> getAllDeniedBookings() {
        return getAllBookingsByStatus(BookingStatus.DENIED);
    }

    @Override
    public List<BookingsDto> getAllStandbyBookings() {
        return getAllBookingsByStatus(BookingStatus.STANDBY);
    }

}