package es.profile.rooms.model.entities;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;
    private String nameRoom;
    private String size;
    private String capacity;
    private int priceHour;
    private String availability;
    private String description;
    private String pictureList;
    private Boolean available = true;
    @ManyToOne
    @JoinColumn(name = "id_office")
    private Offices office;

}
