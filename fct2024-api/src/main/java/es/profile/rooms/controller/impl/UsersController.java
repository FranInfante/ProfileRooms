package es.profile.rooms.controller.impl;

import es.profile.rooms.controller.UsersApi;
import es.profile.rooms.model.dto.UsersCreateDto;
import es.profile.rooms.model.dto.UsersDTO;
import es.profile.rooms.model.dto.UsersUpdateDto;
import es.profile.rooms.service.UsersService;
import es.profile.rooms.util.UserJwt;
import es.profile.rooms.util.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UsersService usersService;

    @Override
    public ResponseEntity<UsersDTO> getUserById(Integer id) {
        UsersDTO user = usersService.getUserById(id).get();
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<UsersDTO>> searchUsers(String username) {
        List<UsersDTO> listName = usersService.searchUsersByUserName(username);
        return ResponseEntity.ok(listName);
    }

    @Override
    public ResponseEntity<UsersCreateDto> createUser(UsersCreateDto newUser) throws Exception {
        try {
            UsersCreateDto createdUser = usersService.createUser(newUser).get();
            return ResponseEntity.ok(createdUser);
        } catch (UserException userException) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<UsersDTO> createUserAdmin(UsersDTO newUser) throws Exception {
        UsersDTO createdUser = usersService.createUserAdmin(newUser).get();
        return ResponseEntity.ok(createdUser);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer id) {
        usersService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateUser(Integer id, UsersUpdateDto updateUser) throws Exception {
        usersService.updateUser(id, updateUser);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateAdminUser(Integer id, UsersDTO updateAdminUser) throws Exception {
        usersService.updateAdminUser(id, updateAdminUser);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UsersDTO> loginUser(UsersDTO userDTO) {
        Optional<UsersDTO> loggedInUser = usersService.loginUser(userDTO.getEmail(), userDTO.getPassword());
        return loggedInUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserJwt> authUser(@RequestBody UsersDTO userDTO) throws Exception {
        UserJwt userJwt = usersService.createAuthenticationToken(userDTO);

        return ResponseEntity.ok(userJwt);
    }

    @Override
    public ResponseEntity<UsersDTO> getUserInfo() {
        UsersDTO usersDTO = usersService.getUserInformation().get();
        return ResponseEntity.ok(usersDTO);
    }
}
