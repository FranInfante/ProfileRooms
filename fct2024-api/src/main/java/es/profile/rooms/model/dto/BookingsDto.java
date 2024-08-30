package es.profile.rooms.model.dto;

import es.profile.rooms.model.entities.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingsDto {

    private Integer id;
    private LocalDateTime ticketTime;
    private LocalDate bookingDate;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Float totalPrice;
    private RoomsDto room;
    private UsersDTO user;
    private List<ExtrasDto> extras;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;


}
