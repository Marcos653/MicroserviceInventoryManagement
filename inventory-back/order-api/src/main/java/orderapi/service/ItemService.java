package orderapi.service;

import lombok.RequiredArgsConstructor;
import orderapi.dto.ItemRequest;
import orderapi.dto.ItemResponse;
import orderapi.model.Item;
import orderapi.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream().map(ItemResponse::of).collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        return ItemResponse.of(itemRepository.findById(id).get());
    }

    @Transactional
    public ItemResponse updateItem(ItemRequest request, Long id) {
        var item = itemRepository.findById(id).get();
        BeanUtils.copyProperties(request, item);
        return ItemResponse.of(itemRepository.save(item));
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
