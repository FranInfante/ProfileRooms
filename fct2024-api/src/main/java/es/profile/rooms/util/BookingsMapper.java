package es.profile.rooms.util;

import es.profile.rooms.model.dto.BookingsDto;
import es.profile.rooms.model.entities.Bookings;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass

public class BookingsMapper {
    public BookingsDto toDTO(Bookings bookings) {
        return BookingsDto.builder()
                .id(bookings.getId())
                .bookingDate(bookings.getBookingDate())
                .ticketTime(bookings.getTicketTime())
                .timeEnd(bookings.getTimeEnd())
                .timeStart(bookings.getTimeStart())
                .totalPrice(bookings.getTotalPrice())
                .room(RoomsMapper.toDTO(bookings.getRoom()))
                .user(UsersMapper.userEntityToDTO(bookings.getUser()))
                .extras(ExtrasMapper.extrasEntityToDto(bookings.getExtras()))
                .status(bookings.getStatus())
                .build();
    }

    public Bookings fromDTO(BookingsDto dto) {
        return Bookings.builder()
                .id(dto.getId())
                .bookingDate(dto.getBookingDate())
                .ticketTime(dto.getTicketTime())
                .timeEnd(dto.getTimeEnd())
                .timeStart(dto.getTimeStart())
                .totalPrice(dto.getTotalPrice())
                .room(RoomsMapper.toEntity(dto.getRoom()))
                .user(UsersMapper.userDTOToEntity(dto.getUser()))
                .extras(ExtrasMapper.extrasDtoToEntityList(dto.getExtras()))
                .status(dto.getStatus())
                .build();
    }

    public List<BookingsDto> toDTOList(List<Bookings> bookingsList) {
        return bookingsList.stream()
                .map(BookingsMapper::toDTO)
                .toList();
    }

    public List<Bookings> fromDTOList(List<BookingsDto> dtoList) {
        return dtoList.stream()
                .map(BookingsMapper::fromDTO)
                .toList();
    }
}
