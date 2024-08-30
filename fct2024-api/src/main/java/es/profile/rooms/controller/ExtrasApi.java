package es.profile.rooms.controller;

import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.util.UriConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.EXTRAS)
public interface ExtrasApi {
    @PostMapping
    ResponseEntity<ExtrasDto> createExtra(@RequestBody ExtrasDto dto);

    @GetMapping
    ResponseEntity<List<ExtrasDto>> getAllExtras();

    @GetMapping(UriConstants.EXTRA_BY_ID)
    ResponseEntity<ExtrasDto> getExtraById(@PathVariable Integer id);

    @PutMapping(UriConstants.EXTRA_BY_ID)
    ResponseEntity<Void> updateExtra(@RequestBody ExtrasDto dto);

    @DeleteMapping(UriConstants.EXTRA_BY_ID)
    ResponseEntity<ExtrasDto> deleteExtra(@PathVariable Integer id);
}



