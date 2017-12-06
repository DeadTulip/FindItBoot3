package p.hh.fiboot3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import p.hh.fiboot3.domain.Team;
import p.hh.fiboot3.domain.User;

import java.util.List;


public interface TeamDao extends JpaRepository<Team, Long> {
    Team findByTeamName(String teamName);
    List<Team> findAllByCreator(User user);
}
