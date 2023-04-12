package ru.clevertec.ecl.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.create.OrderDetails;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.OrderService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository certificateRepository;

    @Override
    public Page<Order> findAllByUserId(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(pageable, userId);
    }

    @Override
    public Order makeOrder(@Valid OrderDetails orderDetails) {
        Optional<User> maybeUser = userRepository.findById(orderDetails.getUserId());
        Optional<GiftCertificate> maybeCertificate = certificateRepository.findById(orderDetails.getGiftCertificateId());

        if(maybeUser.isEmpty() || maybeCertificate.isEmpty()){
            throw new IllegalArgumentException("Invalid User Details Provided");
        }

        return orderRepository.save(Order.builder()
                .setUser(maybeUser.get())
                .setGiftCertificate(maybeCertificate.get())
                .build());
    }
}
