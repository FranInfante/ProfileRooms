package es.profile.rooms.service;


import es.profile.rooms.model.dto.UsersCreateDto;
import es.profile.rooms.model.dto.UsersDTO;
import es.profile.rooms.model.dto.UsersUpdateDto;
import es.profile.rooms.util.UserJwt;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    Optional<UsersDTO> getUserById(Integer id);

    List<UsersDTO> getAllUsers();

    List<UsersDTO> searchUsersByUserName(String userName);

    Optional<UsersDTO> findUserByUsername(String Username);

    Optional<UsersCreateDto> createUser(UsersCreateDto newUser) throws Exception;

    void deleteUserById(Integer userId);

    Optional<UsersUpdateDto> updateUser(Integer UserId, UsersUpdateDto updateUser) throws Exception;

    Optional<UsersDTO> updateAdminUser(Integer UserId, UsersDTO updateAdminUser) throws Exception;

    Optional<UsersDTO> createUserAdmin(UsersDTO newUser) throws Exception;

    Optional<UsersDTO> loginUser(String identifier, String password);

    UserJwt createAuthenticationToken(@RequestBody UsersDTO authenticationRequest) throws Exception;

    Optional<UsersDTO> getUserInformation();
}
