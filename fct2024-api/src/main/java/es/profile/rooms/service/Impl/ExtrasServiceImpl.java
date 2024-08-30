package es.profile.rooms.service.Impl;

import es.profile.rooms.model.dto.ExtrasDto;
import es.profile.rooms.model.entities.Extras;
import es.profile.rooms.repository.ExtrasRepository;
import es.profile.rooms.service.ExtrasService;
import es.profile.rooms.util.ExtrasMapper;
import es.profile.rooms.util.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Angela Rodríguez ~ arodriguez@profile.es
 */
@Service
@RequiredArgsConstructor
public class ExtrasServiceImpl implements ExtrasService {

    private final ExtrasRepository extrasRepository;

    @Override
    public Optional<ExtrasDto> createExtras(ExtrasDto dto) {
        Extras entity = ExtrasMapper.extrasDtoToEntity(dto);
        try {
            Extras savedExtras = extrasRepository.save(entity);
            return Optional.of(ExtrasMapper.extrasEntityToDto(savedExtras));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(MessageConstants.DATA_INTEGRITY_VIOLATION, e);
        }
    }

    @Override
    public Optional<ExtrasDto> getExtraById(Integer id) {
        return extrasRepository.findExtraByIdAndAvailableTrue(id)
                .map(ExtrasMapper::extrasEntityToDto)
                .or(() -> {
                    throw new EntityNotFoundException(MessageConstants.EXTRA_NOT_FOUND);
                });
    }

    @Override
    public List<ExtrasDto> getExtrasByName(String name) {
        List<Extras> extrasList = extrasRepository.findByNameAndAvailableTrue(name);

        return ExtrasMapper.extrasEntityToDto(extrasList);
    }

    @Override
    public List<ExtrasDto> getAllExtras() {
        List<Extras> extrasList = extrasRepository.findByAvailableTrue();
        return ExtrasMapper.extrasEntityToDto(extrasList);
    }


    @Override
    public Optional<ExtrasDto> updateExtras(ExtrasDto dto) {
        try {
            return extrasRepository.findById(dto.getId())
                    .map(extra -> {
                        extra.setDescription(dto.getDescription());
                        extra.setName(dto.getName());
                        extra.setPriceHour(dto.getPriceHour());
                        return Optional.of(ExtrasMapper.extrasEntityToDto(extrasRepository.save(extra)));
                    }).orElseThrow(() -> new EntityNotFoundException(MessageConstants.EXTRA_NOT_FOUND));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(MessageConstants.DATA_INTEGRITY_VIOLATION, e);
        }
    }

    @Override
    public void deleteExtrasById(Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(MessageConstants.ID_NOT_NULL);
        }

        Extras extra = extrasRepository.findById(id).orElseThrow(() -> new NoSuchElementException(MessageConstants.EXTRA_NOT_FOUND));

        extra.setAvailable(false);
        extrasRepository.save(extra);
    }
}


