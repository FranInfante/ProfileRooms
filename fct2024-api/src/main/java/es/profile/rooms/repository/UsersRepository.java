package es.profile.rooms.repository;

import es.profile.rooms.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findUserByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(String Name, String Surname);

    Users findUserByEmailEqualsIgnoreCase(String email);

    Users findUserByUsernameEqualsIgnoreCase(String userName);

    Users findUserByDniEqualsIgnoreCase(String dni);

    List<Users> findUserByUsernameContainingIgnoreCase(String userName);

    List<Users> findUserByRole(String role);

    List<Users> findUserByPhone(String phone);

    List<Users> findAllByAvailableTrue();

    Users findUserByIdAndAvailableTrue(Integer id);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsernameIgnoreCase(String userName);
}
