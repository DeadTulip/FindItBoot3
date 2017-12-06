package p.hh.fiboot3.dto;

import lombok.Data;

@Data
public class ItemDto {

    private Long itemId;
    private UserDto owner;
    private String name;
    private String dateCreated;
    private String dateUpdated;
    private String eventStartTime;
    private String eventEndTime;
    private String involvedPeople;
    private String involvedPlaces;
    private String description;
    private String itemType;
}
