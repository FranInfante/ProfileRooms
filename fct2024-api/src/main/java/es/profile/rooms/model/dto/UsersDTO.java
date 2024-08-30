package es.profile.rooms.model.dto;


import es.profile.rooms.model.entities.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsersDTO {

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
    private Role role;
    private Boolean available = true;
}
