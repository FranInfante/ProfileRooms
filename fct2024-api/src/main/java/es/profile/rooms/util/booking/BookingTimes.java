package es.profile.rooms.util.booking;

import java.time.LocalTime;

public interface BookingTimes {
    LocalTime getTimeStart();

    LocalTime getTimeEnd();
}