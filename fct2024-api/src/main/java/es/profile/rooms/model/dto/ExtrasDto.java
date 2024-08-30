package es.profile.rooms.model.dto;

import lombok.*;

/**
 * @author Angela Rodríguez ~ arodriguez@profile.es
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtrasDto {

    private Integer id;

    private String name;

    private String description;

    private Float priceHour;

    private Boolean available;

}
