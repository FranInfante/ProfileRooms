package es.profile.rooms.util;

import es.profile.rooms.model.dto.OfficesDto;
import es.profile.rooms.model.entities.Offices;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass

public class OfficesMapper {
    public OfficesDto officeMapperEntityToDto(Offices office) {
        return OfficesDto.builder()
                .id(office.getId())
                .province(office.getProvince())
                .city(office.getCity())
                .postcode(office.getPostcode())
                .address(office.getAddress())
                .extras(office.getExtras())
                .available(office.getAvailable())
                .build();
    }

    public Offices officeMapperDtoToEntity(OfficesDto officeDto) {
        return Offices.builder()
                .id(officeDto.getId())
                .province(officeDto.getProvince())
                .city(officeDto.getCity())
                .postcode(officeDto.getPostcode())
                .address(officeDto.getAddress())
                .extras(officeDto.getExtras())
                .available(officeDto.getAvailable())
                .build();
    }

    public List<OfficesDto> officeMapperEntityListToDtoList(List<Offices> officesList) {
        return officesList.stream()
                .map(OfficesMapper::officeMapperEntityToDto)
                .toList();
    }
}
