package p.hh.fiboot3.controller;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot3.dto.*;
import p.hh.fiboot3.exception.DuplicateResourceException;
import p.hh.fiboot3.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
@Getter @Setter
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto, HttpServletResponse response) {
        UserDto returnedUserDto;
        try {
            returnedUserDto = userService.createUser(userDto);
            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (DuplicateResourceException dre) {
            returnedUserDto = new UserDto();
            returnedUserDto.setErrorMessage(dre.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return returnedUserDto;
    }

    @GetMapping("/{userId}")
    public UserDto readUser(@PathVariable Long userId, HttpServletResponse response) {
        UserDto userDto = userService.getUser(userId);
        if (userDto != null) {
            return userDto;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> readAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}/teams")
    public List<TeamDto> getUserTeams(@PathVariable Long userId) {
        return userService.getUserTeams(userId);
    }


    @GetMapping("/{userId}/items")
    public List<ItemDto> getUserItems(@PathVariable Long userId) {
        return userService.getAccessibleItems(userId);
    }

}
