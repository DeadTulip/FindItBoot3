package p.hh.fiboot3.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Team extends BaseEntity {

    @Column
    private String teamName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_user",
            joinColumns = { @JoinColumn(name = "team_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false) }
    )
    private final Set<User> members = new HashSet<User>();

    @ManyToMany
    @JoinTable(
            name = "team_item",
            joinColumns = { @JoinColumn(name = "team_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "item_id", nullable = false) }
    )
    private final Set<Item> items = new HashSet<>();
}
