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
public class DigitalItem extends Item {

    @Column
    private String originalFileName;

    @Column
    private String fileName;

    @Column
    private String fileType;

    @Column
    private Long fileSize;

}
