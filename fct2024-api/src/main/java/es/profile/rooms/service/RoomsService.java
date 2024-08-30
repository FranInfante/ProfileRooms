package es.profile.rooms.service;

import es.profile.rooms.model.dto.RoomsDto;

import java.util.List;
import java.util.Optional;

public interface RoomsService {

    List<RoomsDto> findAllRooms();

    Optional<RoomsDto> findRoomById(Integer id);

    List<RoomsDto> findRoomsByNameContainingIgnoreCase(String name);

    List<RoomsDto> findRoomsBySize(String size);

    List<RoomsDto> findRoomsByCapacity(String capacity);

    List<RoomsDto> findRoomsByPriceHour(int priceHour);

    List<RoomsDto> findRoomsByAvailability(String availability);

    List<RoomsDto> findRoomsByOfficeCity(String city);

    Optional<RoomsDto> saveRooms(RoomsDto roomsDto);

    Optional<RoomsDto> updateRooms(Integer RoomId, RoomsDto roomsDto);

    void deleteRooms(Integer id);

}
