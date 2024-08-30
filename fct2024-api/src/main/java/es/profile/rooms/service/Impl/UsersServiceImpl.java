package es.profile.rooms.service.Impl;

import es.profile.rooms.jwt.JwtUserDetailsService;
import es.profile.rooms.model.dto.UsersCreateDto;
import es.profile.rooms.model.dto.UsersDTO;
import es.profile.rooms.model.dto.UsersUpdateDto;
import es.profile.rooms.model.entities.Users;
import es.profile.rooms.repository.UsersRepository;
import es.profile.rooms.service.UsersService;
import es.profile.rooms.util.DniValidator;
import es.profile.rooms.util.MessageConstants;
import es.profile.rooms.util.UserJwt;
import es.profile.rooms.util.UsersMapper;
import es.profile.rooms.util.exception.UserException;
import es.profile.rooms.util.jwt.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static es.profile.rooms.model.entities.Role.USER;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Optional<UsersDTO> getUserById(Integer id) {
        return Optional.ofNullable(usersRepository.findUserByIdAndAvailableTrue(id))
                .map(UsersMapper::userEntityToDTO)
                .or(() -> {
                    throw new NoSuchElementException(MessageConstants.USER_NOT_AVAILABLE);
                });
    }

    @Override
    public List<UsersDTO> getAllUsers() {
        return UsersMapper.listUserEntityToDto(usersRepository.findAllByAvailableTrue());
    }

    @Override
    public List<UsersDTO> searchUsersByUserName(String userName) {
        List<Users> usersToFind = usersRepository.findUserByUsernameContainingIgnoreCase(userName);

        return UsersMapper.listUserEntityToDto(usersToFind
                .stream()
                .filter(Users::getAvailable)
                .toList());
    }

    @Override
    public Optional<UsersDTO> findUserByUsername(String userName) {
        Users user = usersRepository.findUserByUsernameEqualsIgnoreCase(userName);
        if (user.getAvailable()) {
            return Optional.of(UsersMapper.userEntityToDTO(user));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UsersCreateDto> createUser(UsersCreateDto newUser) throws Exception {
        newUser.setRole(USER);
        newUser.setAvailable(true);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (Objects.nonNull(usersRepository.findUserByUsernameEqualsIgnoreCase(newUser.getUsername()))) {
            throw UserException.userNameExistingException();
        }

        if (Objects.nonNull(usersRepository.findUserByEmailEqualsIgnoreCase(newUser.getEmail()))) {
            throw UserException.emailExistingException();
        }

        Users savedUser = usersRepository.save(UsersMapper.usersCreateDtoToEntity(newUser));
        return Optional.of(UsersMapper.usersCreateEntityToDto(savedUser));
    }

    @Override
    public Optional<UsersDTO> createUserAdmin(UsersDTO newUser) throws Exception {
        newUser.setAvailable(true);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (Objects.nonNull(usersRepository.findUserByUsernameEqualsIgnoreCase(newUser.getUsername()))) {
            throw UserException.userNameExistingException();
        }

        if (Objects.nonNull(usersRepository.findUserByEmailEqualsIgnoreCase(newUser.getEmail()))) {
            throw UserException.emailExistingException();
        }

        if (Objects.nonNull(usersRepository.findUserByDniEqualsIgnoreCase(newUser.getDni()))) {
            throw UserException.dniExistingException(newUser.getDni());
        }

        return Optional.of(UsersMapper.userEntityToDTO(usersRepository.save(UsersMapper.userDTOToEntity(newUser))));
    }

    @Override
    public void deleteUserById(Integer userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));
        user.setAvailable(false);
        usersRepository.save(user);
    }

    @Override
    public Optional<UsersUpdateDto> updateUser(Integer userId, UsersUpdateDto updateUser) throws Exception {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));

        Users updatedUser = UsersMapper.usersUpdateDtoToEntity(updateUser);
        updatedUser.setId(userId);
        updatedUser.setRole(user.getRole());
        updatedUser.setAvailable(true);

        if (!DniValidator.isValidDni(updateUser.getDni())) {
            throw UserException.dniExistingException(updateUser.getDni());
        }
        updatedUser.setDni(updateUser.getDni());

        Users savedUser = usersRepository.save(updatedUser);
        return Optional.of(UsersMapper.usersUpdateEntityToDto(savedUser));
    }

    @Override
    public Optional<UsersDTO> updateAdminUser(Integer userId, UsersDTO updateAdminUser) throws Exception {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));

        user.setName(updateAdminUser.getName());
        user.setSurname(updateAdminUser.getSurname());
        user.setUsername(updateAdminUser.getUsername());
        user.setPassword(updateAdminUser.getPassword());
        user.setEmail(updateAdminUser.getEmail());

        if (!DniValidator.isValidDni(updateAdminUser.getDni())) {
            throw UserException.dniExistingException(updateAdminUser.getDni());
        }
        user.setDni(updateAdminUser.getDni());

        user.setPhone(updateAdminUser.getPhone());
        user.setPostcode(updateAdminUser.getPostcode());
        user.setAddress(updateAdminUser.getAddress());
        user.setRole(updateAdminUser.getRole());
        user.setAvailable(updateAdminUser.getAvailable());

        Users savedUser = usersRepository.save(user);
        return Optional.of(UsersMapper.userEntityToDTO(savedUser));
    }

    @Override
    public Optional<UsersDTO> loginUser(String identifier, String password) {
        Optional<Users> user = usersRepository.findByEmail(identifier)
                .or(() -> usersRepository.findByUsernameIgnoreCase(identifier));

        return user.filter(u -> password.equals(u.getPassword()))
                .map(UsersMapper::userEntityToDTO);
    }

    @Override
    public UserJwt createAuthenticationToken(UsersDTO authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return UserJwt.builder()
                .token(token)
                .build();
    }

    @Override
    public Optional<UsersDTO> getUserInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Users> dbUser = usersRepository.findByUsernameIgnoreCase(user.getUsername());

        return Optional.ofNullable(UsersMapper.userEntityToDTO(dbUser.get()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
