package orderapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import orderapi.dto.OrderRequest;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<Item> items = new ArrayList<>();

    private String status;

    public static Order of(OrderRequest request) {
        var order = new Order();
        order.setItems(
                request.getItems()
                        .stream()
                        .map(itemRequest -> Item.of(itemRequest, order))
                        .collect(Collectors.toList())
        );
        return order;
    }

}
