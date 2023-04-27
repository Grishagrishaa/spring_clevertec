package ru.clevertec.ecl.integration.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.create.OrderDetails;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.service.impl.OrderServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
class OrderServiceImplTest extends BaseIntegrationTest {

    private final OrderServiceImpl service;


    @Test
    void findAllByUserIdShouldReturnExpectedCountEntities() {

        Page<Order> actual = service.findAllByUserId(Pageable.unpaged(), LAST_ENTITY_ID);

        assertThat(actual.getTotalElements()).isEqualTo(1);

    }

    @Test
    void findAllByUserIdShouldTrowExceptionIfInvalidIdProvided() {

        Page<Order> allByUserId = service.findAllByUserId(Pageable.unpaged(), LAST_ENTITY_ID + 1);

        assertThat(allByUserId.getContent()).isEmpty();


    }

    @Test
    void makeOrderShouldReturnInstanceOfOrder() {

        Order actual = service.makeOrder(OrderDetails.of(LAST_ENTITY_ID - 1, LAST_ENTITY_ID - 1));

        assertThat(actual).isInstanceOf(Order.class);

    }

    @Test
    void makeOrderShouldThrowExceptionIfOrderExists() {

        assertThatThrownBy(() -> service.makeOrder(OrderDetails.of(LAST_ENTITY_ID + 1, LAST_ENTITY_ID + 1)))
                .isInstanceOf(IllegalArgumentException.class);

    }

}