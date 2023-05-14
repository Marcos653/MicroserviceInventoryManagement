package inventoryapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private List<ItemDTO> items;
    private String status;
}
