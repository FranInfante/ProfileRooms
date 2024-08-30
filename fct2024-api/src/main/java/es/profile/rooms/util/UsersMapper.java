package es.profile.rooms.util;

import es.profile.rooms.model.dto.UsersCreateDto;
import es.profile.rooms.model.dto.UsersDTO;
import es.profile.rooms.model.dto.UsersUpdateDto;
import es.profile.rooms.model.entities.Users;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UsersMapper {

    public Users userDTOToEntity(UsersDTO usersDTO) {
        return Users.builder()
                .id(usersDTO.getId())
                .name(usersDTO.getName())
                .username(usersDTO.getUsername())
                .surname(usersDTO.getSurname())
                .password(usersDTO.getPassword())
                .email(usersDTO.getEmail())
                .dni(usersDTO.getDni())
                .phone(usersDTO.getPhone())
                .postcode(usersDTO.getPostcode())
                .address(usersDTO.getAddress())
                .role(usersDTO.getRole())
                .available(usersDTO.getAvailable())
                .build();
    }

    public UsersDTO userEntityToDTO(Users user) {
        return UsersDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .dni(user.getDni())
                .phone(user.getPhone())
                .postcode(user.getPostcode())
                .address(user.getAddress())
                .role(user.getRole())
                .available(user.getAvailable())
                .build();
    }

    public List<Users> listUserDTOtoEntity(List<UsersDTO> listUsersDto) {
        return listUsersDto.stream().map(UsersMapper::userDTOToEntity).toList();
    }

    public List<UsersDTO> listUserEntityToDto(List<Users> listUsers) {
        return listUsers.stream().map(UsersMapper::userEntityToDTO).toList();
    }

    public Users usersCreateDtoToEntity(UsersCreateDto userCreateDto) {
        return Users.builder()
                .username(userCreateDto.getUsername())
                .password(userCreateDto.getPassword())
                .email(userCreateDto.getEmail())
                .role(userCreateDto.getRole())
                .available(userCreateDto.getAvailable())
                .build();
    }

    public UsersCreateDto usersCreateEntityToDto(Users user) {
        return UsersCreateDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    public List<Users> listUserCreateDtoToEntity(List<UsersCreateDto> listUsersCreateDto) {
        return listUsersCreateDto.stream().map(UsersMapper::usersCreateDtoToEntity).toList();
    }

    public List<UsersCreateDto> listUserCreateEntityToDto(List<Users> listUsers) {
        return listUsers.stream().map(UsersMapper::usersCreateEntityToDto).toList();
    }

    public Users usersUpdateDtoToEntity(UsersUpdateDto usersUpdateDto) {
        return Users.builder()
                .name(usersUpdateDto.getName())
                .username(usersUpdateDto.getUsername())
                .surname(usersUpdateDto.getSurname())
                .password(usersUpdateDto.getPassword())
                .email(usersUpdateDto.getEmail())
                .dni(usersUpdateDto.getDni())
                .phone(usersUpdateDto.getPhone())
                .postcode(usersUpdateDto.getPostcode())
                .address(usersUpdateDto.getAddress())
                .available(usersUpdateDto.getAvailable())
                .build();
    }

    public UsersUpdateDto usersUpdateEntityToDto(Users user) {
        return UsersUpdateDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .dni(user.getDni())
                .phone(user.getPhone())
                .postcode(user.getPostcode())
                .address(user.getAddress())
                .available(user.getAvailable())
                .build();
    }

    public List<Users> listUsersUpdateDtoToEntity(List<UsersUpdateDto> listUsersUpdateDto) {
        return listUsersUpdateDto.stream().map(UsersMapper::usersUpdateDtoToEntity).toList();
    }

    public List<UsersUpdateDto> listUserUpdateEntityToDto(List<Users> listUsers) {
        return listUsers.stream().map(UsersMapper::usersUpdateEntityToDto).toList();
    }
}
