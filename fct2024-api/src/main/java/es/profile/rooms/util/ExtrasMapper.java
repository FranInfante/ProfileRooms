package es.profile.rooms.util;

import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.model.entities.Extras;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Angela Rodríguez ~ arodriguez@profile.es
 */
@UtilityClass
public class ExtrasMapper {

    public ExtrasDto extrasEntityToDto(Extras entity) {
        return ExtrasDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .priceHour(entity.getPriceHour())
                .available(entity.getAvailable())
                .build();
    }


    public Extras extrasDtoToEntity(ExtrasDto dto) {
        return Extras.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .priceHour(dto.getPriceHour())
                .available(dto.getAvailable())
                .build();
    }


    public List<Extras> extrasDtoToEntityList(List<ExtrasDto> dtoList) {
        return dtoList.stream().map(ExtrasMapper::extrasDtoToEntity).toList();
    }


    public List<ExtrasDto> extrasEntityToDto(List<Extras> entityList) {
        return entityList.stream().map(ExtrasMapper::extrasEntityToDto).toList();
    }

    public Set<ExtrasDto> setExtrasEntityToDto(Set<Extras> extraList) {
        return extraList.stream().map(ExtrasMapper::extrasEntityToDto).collect(Collectors.toSet());
    }

    public Set<Extras> setExtrasDtoToEntity(Set<ExtrasDto> extraList) {
        return extraList.stream().map(ExtrasMapper::extrasDtoToEntity).collect(Collectors.toSet());
    }
}
