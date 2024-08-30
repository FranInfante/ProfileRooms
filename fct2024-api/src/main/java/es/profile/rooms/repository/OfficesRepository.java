package es.profile.rooms.repository;

import es.profile.rooms.model.entities.Offices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficesRepository extends JpaRepository<Offices, Integer> {

    List<Offices> findByProvinceContainingIgnoreCase(String province);

    List<Offices> findByAvailableTrue();

    List<Offices> findByProvinceContainingIgnoreCaseAndAvailableTrue(String province);

    Optional<Offices> findByIdAndAvailableTrue(Integer id);
}
