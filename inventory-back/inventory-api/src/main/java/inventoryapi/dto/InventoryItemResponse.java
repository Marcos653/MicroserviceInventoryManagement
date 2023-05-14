package inventoryapi.dto;

import inventoryapi.model.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public static InventoryItemResponse of(InventoryItem inventoryItem) {
        var response = new InventoryItemResponse();
        BeanUtils.copyProperties(inventoryItem, response);
        return response;
    }
}
