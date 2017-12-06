package p.hh.fiboot3.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto extends BaseDto {

    private Long teamId;
    private String teamName;
    private UserDto creator;
    private List<UserDto> members;
}
