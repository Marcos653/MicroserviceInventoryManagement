package orderapi.controller;

import lombok.RequiredArgsConstructor;
import orderapi.dto.OrderRequest;
import orderapi.dto.OrderResponse;
import orderapi.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        var orders = service.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        return new ResponseEntity<>(service.createOrder(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderResponse> update(@RequestBody OrderRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateOrder(request, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

