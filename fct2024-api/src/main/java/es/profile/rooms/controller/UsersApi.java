package es.profile.rooms.controller;

import es.profile.rooms.model.dto.UsersCreateDto;
import es.profile.rooms.model.dto.UsersDTO;
import es.profile.rooms.model.dto.UsersUpdateDto;
import es.profile.rooms.util.UriConstants;
import es.profile.rooms.util.UserJwt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.USERS)
public interface UsersApi {

    @GetMapping(UriConstants.USER_BY_ID)
    ResponseEntity<UsersDTO> getUserById(@PathVariable Integer id);

    @GetMapping
    ResponseEntity<List<UsersDTO>> getAllUsers();

    @GetMapping(UriConstants.USERS_SEARCH)
    ResponseEntity<List<UsersDTO>> searchUsers(@RequestParam String username);

    @PostMapping
    ResponseEntity<UsersCreateDto> createUser(@RequestBody UsersCreateDto newUser) throws Exception;

    @PostMapping(UriConstants.ADMIN_CREATE_USER)
    ResponseEntity<UsersDTO> createUserAdmin(@RequestBody UsersDTO newUser) throws Exception;

    @PostMapping(UriConstants.USERS_LOGIN)
    ResponseEntity<UsersDTO> loginUser(@RequestBody UsersDTO userDTO);

    @DeleteMapping(UriConstants.USER_BY_ID)
    ResponseEntity<Void> deleteUserById(@PathVariable Integer id);

    @PutMapping(UriConstants.USER_BY_ID)
    ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody UsersUpdateDto updateUser) throws Exception;

    @PutMapping(UriConstants.USERADMIN_BY_ID)
    ResponseEntity<Void> updateAdminUser(@PathVariable Integer id, @RequestParam UsersDTO updateAdminUser) throws Exception;

    @PostMapping(UriConstants.USERS_AUTH)
    ResponseEntity<UserJwt> authUser(@RequestBody UsersDTO userDTO) throws Exception;

    @GetMapping(UriConstants.USERS_INFO)
    ResponseEntity<UsersDTO> getUserInfo() throws Exception;
}
