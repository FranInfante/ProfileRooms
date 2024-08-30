package es.profile.rooms.service;

import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.model.dto.OfficesDto;

import java.util.List;
import java.util.Optional;

public interface OfficesService {

    Optional<OfficesDto> getOfficeById(Integer id);

    List<OfficesDto> getAllOffices();

    List<OfficesDto> getOfficesByProvince(String province);

    void deleteOfficeById(Integer id);

    Optional<OfficesDto> createOffice(OfficesDto office);

    Optional<OfficesDto> updateOfficeById(Integer id, OfficesDto office);

    List<ExtrasDto> getExtrasByOfficeId(Integer id);
}
