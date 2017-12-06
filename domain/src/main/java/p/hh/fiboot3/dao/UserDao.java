package p.hh.fiboot3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import p.hh.fiboot3.domain.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
