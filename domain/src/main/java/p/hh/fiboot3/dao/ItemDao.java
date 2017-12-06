package p.hh.fiboot3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import p.hh.fiboot3.domain.Item;
import p.hh.fiboot3.domain.User;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Long> {
    List<Item> findAllByOwner(User user);
}
