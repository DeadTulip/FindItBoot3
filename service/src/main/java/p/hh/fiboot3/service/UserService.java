package p.hh.fiboot3.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot3.dao.UserDao;
import p.hh.fiboot3.domain.User;
import p.hh.fiboot3.dto.*;
import p.hh.fiboot3.exception.DuplicateResourceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto getUser(Long userId) {
        User user = userDao.findOne(userId);
        return user != null ? modelMapper.map(user, UserDto.class) : null;
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userDao.findAll();
        return MappingUtil.mapUserList(modelMapper, userList);
    }

    public UserDto createUser(UserDto userDto) throws DuplicateResourceException {
        User userWithName = userDao.findByUsername(userDto.getUsername());
        if (userWithName != null) {
            throw new DuplicateResourceException("User", userDto.getUsername());
        }
        User user = modelMapper.map(userDto, User.class);
        userDao.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUser(UserDto userDto) {
        User userToBeUpdated = userDao.findOne(userDto.getUserId());
        userToBeUpdated.setUsername(userDto.getUsername());
        User updatedUser = userDao.save(userToBeUpdated);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    public void deleteUser(Long userId) {
        User user = userDao.findOne(userId);
        if (user != null) {
            itemService.deleteAllItemsCreatedByUser(userId);
            teamService.deleteAllTeamsCreatedByUser(userId);
            userDao.delete(user);
        }
    }

    public List<TeamDto> getUserTeams(Long userId) {
        List<TeamDto> teams = new ArrayList<>();
        User user = userDao.findOne(userId);
        if(user != null) {

            List<TeamDto> createdTeamList = teamService.getAllTeamsCreatedByUser(userId);
            teams.addAll(createdTeamList);

            List<TeamDto> joinedTeamList = MappingUtil.mapTeamList(modelMapper, new ArrayList<>(user.getJoinedTeams()));
            teams.addAll(joinedTeamList);

        }
        return teams;
    }

    public List<ItemDto> getAccessibleItems(Long userId) {
        List<ItemDto> sharedItems = itemService.getAllShareItemsByUser(userId);
        List<ItemDto> createdItems = itemService.getAllItemsCreatedByUser(userId);
        return Stream.concat(sharedItems.stream(), createdItems.stream()).distinct().collect(Collectors.toList());
    }

    public ItemAddInfoDto toAddItem(Long userId) {
        ItemAddInfoDto dto = new ItemAddInfoDto();
        List<String> teamNameList = this.getUserTeams(userId)
                .stream().map(TeamDto::getTeamName).collect(Collectors.toList());
        dto.setSharedTeamsOptions(teamNameList);

        List<ItemDto> createdItemList = itemService.getAllItemsCreatedByUser(userId);
        List<String> involvedPeopleOptions = createdItemList.stream()
                .map(ItemDto::getInvolvedPeople)
                .flatMap(it -> Arrays.stream(it.split(",")))
                .distinct()
                .filter(it -> it.trim().length() > 0)
                .sorted()
                .collect(Collectors.toList());
        dto.setInvolvedPeopleOptions(involvedPeopleOptions);

        List<String> involvedPlaceOptions = createdItemList.stream()
                .map(ItemDto::getInvolvedPlaces)
                .flatMap(it -> Arrays.stream(it.split(",")))
                .distinct()
                .filter(it -> it.trim().length() > 0)
                .sorted()
                .collect(Collectors.toList());
        dto.setInvolvedPlaceOptions(involvedPlaceOptions);
        return dto;
    }
}
