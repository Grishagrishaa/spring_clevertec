package ru.clevertec.ecl.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.create.OrderDetails;
import ru.clevertec.ecl.repository.entity.Order;

public interface OrderService {

    Page<Order> findAllByUserId(Pageable pageable, Long userId);

    Order findByUserIdAndOrderId(Long userId, Long orderId);

    Order makeOrder(@Valid OrderDetails orderDetails);

}
