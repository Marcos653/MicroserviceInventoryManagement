package inventoryapi.controller;

import inventoryapi.dto.InventoryItemRequest;
import inventoryapi.dto.InventoryItemResponse;
import inventoryapi.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/inventory")
@CrossOrigin(origins = "*")
public class InventoryItemController {

    private final InventoryService service;

    @GetMapping
    public ResponseEntity<List<InventoryItemResponse>> getAll() {
        var items = service.getAllInventoryItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<InventoryItemResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getInventoryItemById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryItemResponse> create(@RequestBody InventoryItemRequest request) {
        return new ResponseEntity<>(service.createInventoryItem(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<InventoryItemResponse> update(@RequestBody InventoryItemRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateInventoryItem(request, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteInventoryItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
