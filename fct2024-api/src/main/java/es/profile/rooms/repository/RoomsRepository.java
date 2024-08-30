package es.profile.rooms.repository;

import es.profile.rooms.model.entities.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
    Optional<Rooms> findRoomByIdAndAvailableTrue(Integer id);

    List<Rooms> findByNameRoomContainingIgnoreCaseAndAvailableTrue(String name);

    List<Rooms> findBySizeAndAvailableTrue(String size);

    List<Rooms> findByCapacityAndAvailableTrue(String capacity);

    List<Rooms> findByPriceHourAndAvailableTrue(int priceHour);

    List<Rooms> findByAvailabilityAndAvailableTrue(String availability);

    List<Rooms> findAllByAvailableTrue();


    @Query("SELECT r FROM Rooms r WHERE r.office.city = :city")
    List<Rooms> findByOfficeCityAndAvailableTrue(@Param("city") String city);


}
