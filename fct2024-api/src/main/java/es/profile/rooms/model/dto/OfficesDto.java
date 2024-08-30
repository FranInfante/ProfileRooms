package es.profile.rooms.model.dto;

import es.profile.rooms.model.entities.Extras;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class OfficesDto {

    private Integer id;
    private String province;
    private String city;
    private String postcode;
    private String address;
    private List<Extras> extras;
    private Boolean available;


}
