package es.profile.rooms.repository;

import es.profile.rooms.model.entities.Extras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExtrasRepository extends JpaRepository<Extras, Integer> {

    List<Extras> findByName(String name);

    Optional<Extras> findExtraByIdAndAvailableTrue(Integer id);

    List<Extras> findByNameAndAvailableTrue(String name);

    List<Extras> findByAvailableTrue();
}
