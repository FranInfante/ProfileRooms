package es.profile.rooms.controller.impl;

import es.profile.rooms.controller.RoomsApi;
import es.profile.rooms.model.dto.RoomsDto;
import es.profile.rooms.service.RoomsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomsController implements RoomsApi {

    private final RoomsService roomsService;

    @Override
    public ResponseEntity<List<RoomsDto>> getAllRooms() {
        return ResponseEntity.ok(roomsService.findAllRooms());
    }

    @Override
    public ResponseEntity<RoomsDto> findRoomById(Integer id) {
        RoomsDto room = roomsService.findRoomById(id).get();
        return ResponseEntity.ok(room);
    }

    @Override
    public ResponseEntity<List<RoomsDto>> getRoomsByName(String name) {
        return ResponseEntity.ok(roomsService.findRoomsByNameContainingIgnoreCase(name));
    }

    @Override
    public ResponseEntity<List<RoomsDto>> getRoomsSize(String size) {
        return ResponseEntity.ok(roomsService.findRoomsBySize(size));
    }

    @Override
    public ResponseEntity<List<RoomsDto>> getRoomsCapacity(Integer capacity) {
        return ResponseEntity.ok(roomsService.findRoomsByCapacity(capacity.toString()));
    }

    @Override
    public ResponseEntity<List<RoomsDto>> getRoomsPriceHour(Integer priceHour) {
        return ResponseEntity.ok(roomsService.findRoomsByPriceHour(priceHour));
    }

    @Override
    public ResponseEntity<List<RoomsDto>> getRoomsAvailability(String availability) {
        return ResponseEntity.ok(roomsService.findRoomsByAvailability(availability));
    }

    @Override
    public ResponseEntity<List<RoomsDto>> getRoomsOfficeCity(String officeCity) {
        return ResponseEntity.ok(roomsService.findRoomsByOfficeCity(officeCity));
    }

    @Override
    public ResponseEntity<RoomsDto> createRooms(@RequestBody RoomsDto roomsDto) {
        RoomsDto newRoomsDto = roomsService.saveRooms(roomsDto).get();
        return ResponseEntity.ok(newRoomsDto);
    }

    @Override
    public ResponseEntity<Void> updateRooms(Integer id, RoomsDto roomsDto) {
        roomsService.updateRooms(id, roomsDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<RoomsDto> deleteRoomsById(Integer id) {
        roomsService.deleteRooms(id);
        return ResponseEntity.noContent().build();
    }
}
