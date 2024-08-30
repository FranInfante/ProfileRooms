package es.profile.rooms.model.dto;

import es.profile.rooms.model.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsersUpdateDto {

    private Integer id;

    @NotBlank(message = "name is required")
    @Min(value = 3, message = "Name cannot be less than 3 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Min(value = 3, message = "Surname cannot be less than 3 characters")
    private String surname;

    @NotBlank(message = "Username is required")
    @Min(value = 5, message = "username cannot be less than 5 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Min(value = 6, message = "The password cannot be less than 6 characters")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKET]$", message = "\n" +
            "The DNI must have the following format: 12345678A")
    private String dni;

    @Pattern(regexp = "^[0-9]{9}$", message = "Telephone number must contain 9 digits")
    private String phone;

    @Pattern(regexp = "^[0-9]{5}$", message = "The zip code must contain 5 digits")
    private String postcode;

    @NotBlank(message = "Address is required")
    private String address;

    private Role role;
    private Boolean available = true;

}
