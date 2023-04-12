package ru.clevertec.ecl.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<Order> makeOrder(@RequestBody OrderDetails orderDetails){
        return ResponseEntity.ok(service.makeOrder(orderDetails));
    }

}
