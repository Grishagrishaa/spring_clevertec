package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.repository.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUserId(Pageable pageable, Long userId);

}
