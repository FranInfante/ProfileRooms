package es.profile.rooms.controller.impl;

import es.profile.rooms.controller.ExtrasApi;
import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.service.ExtrasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Angela Rodríguez ~ arodriguez@profile.es
 */
@RestController
@RequiredArgsConstructor
public class ExtrasController implements ExtrasApi {

    private final ExtrasService extrasService;

    @Override
    public ResponseEntity<ExtrasDto> createExtra(@RequestBody ExtrasDto dto) {
        ExtrasDto createdExtra = extrasService.createExtras(dto).get();
        return ResponseEntity.ok(createdExtra);
    }

    @Override
    public ResponseEntity<List<ExtrasDto>> getAllExtras() {
        return ResponseEntity.ok(this.extrasService.getAllExtras());
    }

    @Override
    public ResponseEntity<ExtrasDto> getExtraById(@PathVariable("id") Integer id) {
        ExtrasDto extraDto = extrasService.getExtraById(id).get();
        return ResponseEntity.ok(extraDto);
    }

    @Override
    public ResponseEntity<Void> updateExtra(@RequestBody ExtrasDto dto) {
        this.extrasService.updateExtras(dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ExtrasDto> deleteExtra(@PathVariable("id") Integer id) {
        this.extrasService.deleteExtrasById(id);
        return ResponseEntity.noContent().build();
    }
}
