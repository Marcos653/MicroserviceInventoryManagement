package inventoryapi.model;

import inventoryapi.dto.InventoryItemRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private int quantity;

    public static InventoryItem of(InventoryItemRequest request) {
        var inventoryItem = new InventoryItem();
        BeanUtils.copyProperties(request, inventoryItem);
        return inventoryItem;
    }
}
