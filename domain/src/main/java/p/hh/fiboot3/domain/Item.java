package p.hh.fiboot3.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Item extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column
    private String name;

    @Column
    private Date dateCreated;

    @Column
    private Date dateUpdated;

    @Column
    private Date eventStartTime;

    @Column
    private Date eventEndTime;

    @Column(name = "people")
    private String involvedPeople;

    @Column(name = "places")
    private String involvedPlaces;

    @Column
    private String description;


}
