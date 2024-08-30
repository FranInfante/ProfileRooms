package es.profile.rooms.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;
    private LocalDateTime ticketTime;
    private LocalDate bookingDate;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Float totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    @OneToMany
    private List<Extras> extras;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Rooms room;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;
}