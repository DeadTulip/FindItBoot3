package p.hh.fiboot3.dto;


import org.modelmapper.ModelMapper;
import p.hh.fiboot3.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtil {

    public static List<UserDto> mapUserList(ModelMapper modelMapper, List<User> userList) {
        return userList.stream().map(it -> {
            UserDto dto = modelMapper.map(it, UserDto.class);
            List<String> roles = it.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList());
            dto.setRoles(String.join(",", roles));
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<TeamDto> mapTeamList(ModelMapper modelMapper, List<Team> teamList) {
        return teamList.stream().map(it -> modelMapper.map(it, TeamDto.class)).collect(Collectors.toList());
    }

    public static List<ItemDto> mapItemList(ModelMapper modelMapper, List<Item> itemList) {
        return itemList.stream().map(
                it -> {
                    if (it instanceof PhysicalItem) {
                        return modelMapper.map(it, PhysicalItemDto.class);
                    } else {
                        return modelMapper.map(it, DigitalItemDto.class);
                    }
                }
            ).collect(Collectors.toList());
    }
}
