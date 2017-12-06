package p.hh.fiboot3.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "id")
@Data
@ToString(callSuper = true)
public class PhysicalItem extends Item {

    @Column
    private Float length;

    @Column
    private Float width;

    @Column
    private Float height;
}
