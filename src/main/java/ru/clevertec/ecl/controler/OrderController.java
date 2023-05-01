package ru.clevertec.ecl.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.create.OrderDetails;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.service.OrderService;

@RestController
@RequestMapping("${app.orderController.path}")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<Order>> findAllByUserId(@PageableDefault Pageable pageable,
                                                       @PathVariable Long userId){
        return ResponseEntity.ok(service.findAllByUserId(pageable, userId));
    }

    @GetMapping("/{userId}/order_id/{orderId}")
    public ResponseEntity<Order> findByUserIdAndOrderId(@PathVariable Long userId,
                                                        @PathVariable Long orderId){
        return ResponseEntity.ok(service.findByUserIdAndOrderId(userId, orderId));
    }

    @PostMapping
    public ResponseEntity<Order> makeOrder(@RequestBody OrderDetails orderDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.makeOrder(orderDetails));
    }

}
