package ru.clevertec.ecl.repository.listeners;

import jakarta.persistence.PrePersist;
import ru.clevertec.ecl.repository.entity.Order;

import java.time.Instant;

public class OrderListener {

    @PrePersist
    void setTotalPrice(Order order) {
        Double cost = order.getGiftCertificate().getPrice();

        order.setPurchaseTime(Instant.now());
        order.setCost(cost);
    }

}