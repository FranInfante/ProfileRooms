package es.profile.rooms.service.Impl;

import es.profile.rooms.model.dto.RoomsDto;
import es.profile.rooms.model.entities.Rooms;
import es.profile.rooms.repository.OfficesRepository;
import es.profile.rooms.repository.RoomsRepository;
import es.profile.rooms.service.RoomsService;
import es.profile.rooms.util.MessageConstants;
import es.profile.rooms.util.RoomsMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {
    private final RoomsRepository roomsRepository;

    @Override
    public List<RoomsDto> findAllRooms() {
        return RoomsMapper.listToDto(roomsRepository.findAllByAvailableTrue());
    }

    @Override
    public Optional<RoomsDto> findRoomById(Integer id) {
        return roomsRepository.findRoomByIdAndAvailableTrue(id)
                .map(RoomsMapper::toDTO)
                .or(() -> {
                    throw new EntityNotFoundException(MessageConstants.ROOM_NOT_FOUND);
                });
    }

    @Override
    public List<RoomsDto> findRoomsByNameContainingIgnoreCase(String name) {
        return RoomsMapper.listToDto(roomsRepository.findByNameRoomContainingIgnoreCaseAndAvailableTrue(name));
    }

    @Override
    public List<RoomsDto> findRoomsBySize(String size) {
        return RoomsMapper.listToDto(roomsRepository.findBySizeAndAvailableTrue(size));
    }

    @Override
    public List<RoomsDto> findRoomsByCapacity(String capacity) {
        return RoomsMapper.listToDto(roomsRepository.findByCapacityAndAvailableTrue(capacity));
    }

    @Override
    public List<RoomsDto> findRoomsByPriceHour(int priceHour) {
        return RoomsMapper.listToDto(roomsRepository.findByPriceHourAndAvailableTrue(priceHour));
    }

    @Override
    public List<RoomsDto> findRoomsByAvailability(String availability) {
        return RoomsMapper.listToDto(roomsRepository.findByAvailabilityAndAvailableTrue(availability));
    }

    @Override
    public List<RoomsDto> findRoomsByOfficeCity(String city) {
        return RoomsMapper.listToDto(roomsRepository.findByOfficeCityAndAvailableTrue(city));
    }

    @Transactional
    @Override
    public Optional<RoomsDto> saveRooms(RoomsDto roomsDto) {
        Rooms rooms = RoomsMapper.toEntity(roomsDto);
        rooms.setAvailable(true);
        return Optional.of(RoomsMapper.toDTO(roomsRepository.save(rooms)));
    }

    @Transactional
    @Override
    public Optional<RoomsDto> updateRooms(Integer RoomId, RoomsDto roomsDto) {
        return roomsRepository.findRoomByIdAndAvailableTrue(RoomId)
                .map(roomToUpdate -> {
                    Rooms roomNewInfo = RoomsMapper.toEntity(roomsDto);
                    roomToUpdate.setNameRoom(roomNewInfo.getNameRoom());
                    roomToUpdate.setSize(roomNewInfo.getSize());
                    roomToUpdate.setCapacity(roomNewInfo.getCapacity());
                    roomToUpdate.setDescription(roomNewInfo.getDescription());
                    roomToUpdate.setPriceHour(roomNewInfo.getPriceHour());

                    return RoomsMapper.toDTO(roomsRepository.save(roomToUpdate));
                })
                .or(() -> {
                    throw new EntityNotFoundException(MessageConstants.ROOM_NOT_FOUND);
                });
    }

    @Transactional
    @Override
    public void deleteRooms(Integer id) {
        roomsRepository.findRoomByIdAndAvailableTrue(id)
                .ifPresentOrElse(room -> {
                    room.setAvailable(false);
                    roomsRepository.save(room);
                }, () -> {
                    throw new EntityNotFoundException(MessageConstants.ROOM_NOT_FOUND);
                });
    }
}