package p.hh.fiboot3.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class PhysicalItemDto extends ItemDto {

    private Float length;
    private Float width;
    private Float height;
}
