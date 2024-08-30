package es.profile.rooms.util;

import es.profile.rooms.model.dto.RoomsDto;
import es.profile.rooms.model.entities.Rooms;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RoomsMapper {

    public RoomsDto toDTO(Rooms rooms) {
        return RoomsDto.builder()
                .id(rooms.getId())
                .nameRoom(rooms.getNameRoom())
                .size(rooms.getSize())
                .capacity(rooms.getCapacity())
                .priceHour(rooms.getPriceHour())
                .availability(rooms.getAvailability())
                .description(rooms.getDescription())
                .pictureList(rooms.getPictureList())
                .available(rooms.getAvailable())
                .office(OfficesMapper.officeMapperEntityToDto(rooms.getOffice()))
                .build();
    }

    public Rooms toEntity(RoomsDto roomsDTO) {
        return Rooms.builder()
                .id(roomsDTO.getId())
                .nameRoom(roomsDTO.getNameRoom())
                .size(roomsDTO.getSize())
                .capacity(roomsDTO.getCapacity())
                .priceHour(roomsDTO.getPriceHour())
                .availability(roomsDTO.getAvailability())
                .description(roomsDTO.getDescription())
                .pictureList(roomsDTO.getPictureList())
                .available(roomsDTO.getAvailable())
                .office(OfficesMapper.officeMapperDtoToEntity(roomsDTO.getOffice()))
                .build();
    }

    public List<Rooms> listToEntity(List<RoomsDto> roomsDto) {

        return roomsDto.stream().map(RoomsMapper::toEntity).toList();

    }

    public List<RoomsDto> listToDto(List<Rooms> rooms) {

        return rooms.stream().map(RoomsMapper::toDTO).toList();
    }
}
