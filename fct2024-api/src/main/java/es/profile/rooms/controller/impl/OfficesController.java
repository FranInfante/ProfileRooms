package es.profile.rooms.controller.impl;

import es.profile.rooms.controller.OfficesApi;
import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.model.dto.OfficesDto;
import es.profile.rooms.service.OfficesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfficesController implements OfficesApi {

    private final OfficesService officesService;

    @Override
    public ResponseEntity<List<OfficesDto>> getAllOffices() {
        List<OfficesDto> officesDtos = officesService.getAllOffices();
        return ResponseEntity.ok(officesDtos);
    }

    @Override
    public ResponseEntity<OfficesDto> getOfficeById(Integer id) {
        OfficesDto officeDto = officesService.getOfficeById(id).get();
        return ResponseEntity.ok(officeDto);
    }

    @Override
    public ResponseEntity<List<OfficesDto>> getOfficesByProvince(String province) {
        List<OfficesDto> officesDtos = officesService.getOfficesByProvince(province);
        return ResponseEntity.ok(officesDtos);
    }

    @Override
    public ResponseEntity<Void> deleteOfficeById(Integer id) {
        officesService.deleteOfficeById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<OfficesDto> createNewOffice(OfficesDto newOfficeDto) {
        OfficesDto officeDto = officesService.createOffice(newOfficeDto).get();
        return ResponseEntity.ok(officeDto);
    }

    @Override
    public ResponseEntity<Void> updateOffice(Integer id, OfficesDto officeDto) {
        officesService.updateOfficeById(id, officeDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ExtrasDto>> getExtrasByOfficeId(Integer id) {
        List<ExtrasDto> Extras = officesService.getExtrasByOfficeId(id);
        return new ResponseEntity<>(Extras, HttpStatus.OK);
    }
}
