package p.hh.fiboot3.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Role extends BaseEntity {

    @Column
    private String authority;
}
