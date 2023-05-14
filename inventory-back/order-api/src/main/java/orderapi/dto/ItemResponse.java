package orderapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import orderapi.model.Item;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public static ItemResponse of(Item item) {
        var response = new ItemResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }
}
