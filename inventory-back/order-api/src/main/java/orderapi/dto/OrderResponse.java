package orderapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import orderapi.model.Order;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private List<ItemResponse> items;
    private String status;

    public static OrderResponse of(Order order) {
        var response = new OrderResponse();
        BeanUtils.copyProperties(order, response);
        response.setItems(order.getItems()
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList()));
        return response;
    }
}
