package p.hh.fiboot3.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemAddInfoDto {
    private List<String> sharedTeamsOptions = new ArrayList<>();
    private List<String> involvedPeopleOptions = new ArrayList<>();
    private List<String> involvedPlaceOptions = new ArrayList<>();
}
