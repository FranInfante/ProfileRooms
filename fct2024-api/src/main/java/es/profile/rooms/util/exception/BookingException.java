package es.profile.rooms.util.exception;

import es.profile.rooms.util.MessageConstants;
import jakarta.persistence.EntityNotFoundException;

public class BookingException extends Exception {
    public BookingException(String s) {
        super(s);
    }

    public static EntityNotFoundException bookingNotFound() {
        return new EntityNotFoundException(MessageConstants.BOOKING_NOT_FOUND);
    }

    public static BookingException bookingTimeConflict() {
        return new BookingException(MessageConstants.BOOKING_TIME_CONFLICT);
    }
}
