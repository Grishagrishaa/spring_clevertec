package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.create.OrderDetails;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.testUtils.builder.impl.OrderTestBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final Long ID = 9L;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GiftCertificateRepository certificateRepository;
    @InjectMocks
    private OrderServiceImpl service;


    @ParameterizedTest
    @MethodSource("provideOrder")
    void findAllByUserId(Order expectedOrder) {
        Page<Order> expectedOrders = new PageImpl<>(List.of(expectedOrder));

        doReturn(expectedOrders)
                .when(orderRepository).findAllByUserId(any(Pageable.class), eq(ID));

        Page<Order> actual = service.findAllByUserId(Pageable.unpaged(), ID);

        assertThat(actual).isEqualTo(expectedOrders);
    }

    @ParameterizedTest
    @MethodSource("provideOrder")
    void makeOrderShouldReturnExpectedOrder(Order expectedOrder) {
        OrderDetails orderDetails = OrderDetails.of(1L, 2L);

        doReturn(Optional.of(expectedOrder.getUser()))
                .when(userRepository).findById(orderDetails.getUserId());
        doReturn(Optional.of(expectedOrder.getGiftCertificate()))
                .when(certificateRepository).findById(orderDetails.getGiftCertificateId());
        doReturn(expectedOrder)
                .when(orderRepository).save(any(Order.class));

        Order actual = service.makeOrder(orderDetails);

        assertThat(actual).isEqualTo(expectedOrder);
    }

    private static Stream<Arguments> provideOrder() {
        return Stream.of(
                Arguments.of(OrderTestBuilder.defaultValues().build()),
                Arguments.of(OrderTestBuilder.defaultValues().build()),
                Arguments.of(OrderTestBuilder.defaultValues().build()),
                Arguments.of(OrderTestBuilder.defaultValues().build()),
                Arguments.of(OrderTestBuilder.defaultValues().build())
        );
    }
}