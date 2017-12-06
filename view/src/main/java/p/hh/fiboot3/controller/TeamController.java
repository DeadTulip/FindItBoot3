package p.hh.fiboot3.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot3.domain.Team;
import p.hh.fiboot3.dto.TeamDto;
import p.hh.fiboot3.dto.UserDto;
import p.hh.fiboot3.exception.DuplicateResourceException;
import p.hh.fiboot3.exception.ResourceNotFoundException;
import p.hh.fiboot3.service.TeamService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamService teamService;

    @PostMapping
    public TeamDto createTeam(@RequestBody TeamDto teamDto, HttpServletResponse response) {
        TeamDto returnedTeamDto;
        try {
            returnedTeamDto = teamService.createTeam(teamDto);
            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (DuplicateResourceException dre) {
            returnedTeamDto = new TeamDto();
            returnedTeamDto.setErrorMessage(dre.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return returnedTeamDto;
    }

    @GetMapping("/{teamId}")
    public TeamDto readTeam(@PathVariable Long teamId) {
        Team team = teamService.getTeam(teamId);
        return modelMapper.map(team, TeamDto.class);
    }

    @PostMapping("/udpate")
    public TeamDto updateTeam() {
        return null;
    }

    @DeleteMapping("/{teamId}")
    public void deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
    }

    @PutMapping("/{teamId}/addMember/{userName}")
    public UserDto addMember(@PathVariable Long teamId, @PathVariable String userName, HttpServletResponse response) {
        UserDto dto = new UserDto();
        try {
            dto = teamService.addMember(teamId, userName);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch(ResourceNotFoundException rnfe) {
            dto.setErrorMessage(rnfe.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return dto;
    }

    @PutMapping("/{teamId}/removeMember/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.removeMember(teamId, userId);
    }
}
