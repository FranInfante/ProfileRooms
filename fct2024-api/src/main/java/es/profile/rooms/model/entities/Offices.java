package es.profile.rooms.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Offices {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String province;

    private String city;

    private String postcode;

    private String address;

    @ManyToMany
    @JoinTable(
            name = "office_extras",
            joinColumns = @JoinColumn(name = "office_id"),
            inverseJoinColumns = @JoinColumn(name = "extra_id")
    )
    private List<Extras> extras;

    private Boolean available;
}
