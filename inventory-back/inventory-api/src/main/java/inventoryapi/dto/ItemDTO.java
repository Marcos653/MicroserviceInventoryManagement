package inventoryapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
