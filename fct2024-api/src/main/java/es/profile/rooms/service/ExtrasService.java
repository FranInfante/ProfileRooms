package es.profile.rooms.service;

import es.profile.rooms.model.dto.ExtrasDto;

import java.util.List;
import java.util.Optional;

/**
 * @author Angela Rodríguez ~ arodriguez@profile.es
 */

public interface ExtrasService {

    Optional<ExtrasDto> createExtras(ExtrasDto dto);

    Optional<ExtrasDto> getExtraById(Integer id);

    List<ExtrasDto> getExtrasByName(String name);

    List<ExtrasDto> getAllExtras();

    Optional<ExtrasDto> updateExtras(ExtrasDto extras);

    void deleteExtrasById(Integer id);


}
