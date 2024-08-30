package es.profile.rooms.controller;

import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.model.dto.OfficesDto;
import es.profile.rooms.util.UriConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.OFFICES)
public interface OfficesApi {

    @GetMapping
    ResponseEntity<List<OfficesDto>> getAllOffices();

    @GetMapping(UriConstants.OFFICE_BY_ID)
    ResponseEntity<OfficesDto> getOfficeById(@PathVariable Integer id);

    @GetMapping(UriConstants.OFFICES_BY_PROVINCE)
    ResponseEntity<List<OfficesDto>> getOfficesByProvince(@PathVariable String province);

    @DeleteMapping(UriConstants.OFFICE_BY_ID)
    ResponseEntity<Void> deleteOfficeById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<OfficesDto> createNewOffice(@RequestBody OfficesDto newOfficeDto);

    @PutMapping(UriConstants.OFFICE_BY_ID)
    ResponseEntity<Void> updateOffice(@PathVariable Integer id, @RequestBody OfficesDto officeDto);

    @GetMapping("/extras/{id}")
    ResponseEntity<List<ExtrasDto>> getExtrasByOfficeId(@PathVariable Integer id);
}
