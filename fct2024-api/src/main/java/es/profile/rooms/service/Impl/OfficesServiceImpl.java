package es.profile.rooms.service.Impl;

import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.model.dto.OfficesDto;
import es.profile.rooms.model.entities.Extras;
import es.profile.rooms.model.entities.Offices;
import es.profile.rooms.repository.OfficesRepository;
import es.profile.rooms.service.OfficesService;
import es.profile.rooms.util.ExtrasMapper;
import es.profile.rooms.util.MessageConstants;
import es.profile.rooms.util.OfficesMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficesServiceImpl implements OfficesService {

    private final OfficesRepository officesRepository;

    @Override
    public Optional<OfficesDto> getOfficeById(Integer id) {
        return officesRepository.findByIdAndAvailableTrue(id)
                .map(OfficesMapper::officeMapperEntityToDto)
                .or(() -> {
                    throw new EntityNotFoundException(MessageConstants.OFFICE_NOT_FOUND);
                });
    }

    @Override
    public List<OfficesDto> getAllOffices() {
        List<Offices> availableOffices = officesRepository.findByAvailableTrue();
        return OfficesMapper.officeMapperEntityListToDtoList(availableOffices);
    }

    @Override
    public List<ExtrasDto> getExtrasByOfficeId(Integer id) {
        Offices office = officesRepository.findByIdAndAvailableTrue(id)
                .orElseThrow(() -> new NoSuchElementException(MessageConstants.OFFICE_NOT_FOUND));

        return ExtrasMapper.extrasEntityToDto(office.getExtras());
    }

    @Override
    public List<OfficesDto> getOfficesByProvince(String province) {
        List<Offices> availableOffices = officesRepository.findByProvinceContainingIgnoreCaseAndAvailableTrue(province);
        return OfficesMapper.officeMapperEntityListToDtoList(availableOffices);
    }


    @Override
    public void deleteOfficeById(Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(MessageConstants.ID_NOT_NULL);
        }

        Offices office = officesRepository.findById(id).orElseThrow(()-> new NoSuchElementException(MessageConstants.OFFICE_NOT_FOUND));

        office.setAvailable(false);
        officesRepository.save(office);
    }

    @Override
    public Optional<OfficesDto> createOffice(OfficesDto officeDto) {
        Offices office = OfficesMapper.officeMapperDtoToEntity(officeDto);
        return Optional.of(OfficesMapper.officeMapperEntityToDto(officesRepository.save(office)));
    }

    @Override
    public Optional<OfficesDto> updateOfficeById(Integer id, OfficesDto officeDto) {
        return officesRepository.findById(id)
                .map(existingOffice -> {
                    existingOffice.setProvince(officeDto.getProvince());
                    existingOffice.setCity(officeDto.getCity());
                    existingOffice.setPostcode(officeDto.getPostcode());
                    existingOffice.setAddress(officeDto.getAddress());
                    existingOffice.setExtras(officeDto.getExtras());
                    return OfficesMapper.officeMapperEntityToDto(officesRepository.save(existingOffice));
                })
                .or(() -> {
                    throw new EntityNotFoundException(MessageConstants.OFFICE_NOT_FOUND);
                });
    }


}
