package es.profile.rooms.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomsDto {
    private Integer id;
    private String nameRoom;
    private String size;
    private String capacity;
    private int priceHour;
    private String availability;
    private String description;
    private String pictureList;
    private Boolean available = true;
    private OfficesDto office;

}
