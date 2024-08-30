package es.profile.rooms.controller;

import es.profile.rooms.model.dto.RoomsDto;
import es.profile.rooms.util.UriConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.ROOMS)
public interface RoomsApi {
    @GetMapping
    ResponseEntity<List<RoomsDto>> getAllRooms();

    @GetMapping(UriConstants.ROOM_BY_ID)
    ResponseEntity<RoomsDto> findRoomById(@PathVariable Integer id);

    @GetMapping(UriConstants.ROOMS_BY_NAME)
    ResponseEntity<List<RoomsDto>> getRoomsByName(@PathVariable String name);

    @GetMapping(UriConstants.ROOMS_BY_SIZE)
    ResponseEntity<List<RoomsDto>> getRoomsSize(@PathVariable String size);

    @GetMapping(UriConstants.ROOMS_BY_CAPACITY)
    ResponseEntity<List<RoomsDto>> getRoomsCapacity(@PathVariable Integer capacity);

    @GetMapping(UriConstants.ROOMS_BY_PRICE_HOUR)
    ResponseEntity<List<RoomsDto>> getRoomsPriceHour(@PathVariable Integer priceHour);

    @GetMapping(UriConstants.ROOMS_BY_AVAILABILITY)
    ResponseEntity<List<RoomsDto>> getRoomsAvailability(@PathVariable String availability);

    @GetMapping(UriConstants.ROOMS_BY_OFFICE_CITY)
    ResponseEntity<List<RoomsDto>> getRoomsOfficeCity(@PathVariable String officeCity);

    @PostMapping
    ResponseEntity<RoomsDto> createRooms(@RequestBody RoomsDto roomsDto);

    @PutMapping(UriConstants.ROOM_BY_ID)
    ResponseEntity<Void> updateRooms(@PathVariable Integer id, @RequestBody RoomsDto roomsDto);

    @DeleteMapping(UriConstants.ROOM_BY_ID)
    ResponseEntity<RoomsDto> deleteRoomsById(@PathVariable Integer id);

}
