package inventoryapi.service;

import inventoryapi.dto.InventoryItemRequest;
import inventoryapi.dto.InventoryItemResponse;
import inventoryapi.dto.ItemDTO;
import inventoryapi.dto.OrderDTO;
import inventoryapi.model.InventoryItem;
import inventoryapi.repository.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryItemRepository inventoryRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final String ORDER_QUEUE_NAME = "order.queue";
    private static final String CONFIRMATION_QUEUE_NAME = "confirmation.queue";
    private static final String UNAVAILABLE_QUEUE_NAME = "unavailable.queue";

    public List<InventoryItemResponse> getAllInventoryItems() {
        return inventoryRepository.findAll().stream().map(InventoryItemResponse::of).collect(Collectors.toList());
    }

    public InventoryItemResponse getInventoryItemById(Long id) {
        return InventoryItemResponse.of(inventoryRepository.findById(id).get());
    }

    @Transactional
    public InventoryItemResponse createInventoryItem(InventoryItemRequest request) {
        var inventoryItem = InventoryItem.of(request);
        return InventoryItemResponse.of(inventoryRepository.save(inventoryItem));
    }

    @Transactional
    public InventoryItemResponse updateInventoryItem(InventoryItemRequest request, Long id) {
        var inventoryItem = inventoryRepository.findById(id).get();
        BeanUtils.copyProperties(request, inventoryItem, "id");
        return InventoryItemResponse.of(inventoryRepository.save(inventoryItem));
    }

    @Transactional
    public void deleteInventoryItem(Long id) {
        inventoryRepository.deleteById(id);
    }

    @RabbitListener(queues = ORDER_QUEUE_NAME)
    public void processOrder(OrderDTO orderDTO) {
        boolean isOrderAvailable = orderDTO.getItems().stream().allMatch(itemDTO -> {
            var inventoryItemOpt = inventoryRepository.findByName(itemDTO.getName());
            if (inventoryItemOpt.isEmpty()) {
                return false;
            }
            var inventoryItem = inventoryItemOpt.get();
            if (inventoryItem.getQuantity() < itemDTO.getQuantity()) {
                return false;
            }
            inventoryItem.setQuantity(inventoryItem.getQuantity() - itemDTO.getQuantity());
            inventoryRepository.save(inventoryItem);
            return true;
        });

        if (!isOrderAvailable) {
            rabbitTemplate.convertAndSend(UNAVAILABLE_QUEUE_NAME, orderDTO);
        } else {
            rabbitTemplate.convertAndSend(CONFIRMATION_QUEUE_NAME, orderDTO);
        }
    }

}

