package es.profile.rooms.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String dni;
    private String phone;
    private String postcode;
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean available = true;


}




