package p.hh.fiboot3.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class DigitalItemDto extends ItemDto {

    private String originalFileName;
    private String fileName;
    private String fileType;
    private Long fileSize;
}
