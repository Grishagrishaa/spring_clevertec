package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.testUtils.builder.impl.OrderTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class OrderRepositoryImplIntegrationTest extends BaseIntegrationTest {

    private final OrderRepository repository;

    private static final Long LAST_ENTITY_ID = 9L;

    @Test
    void createShouldReturnCreatedEntity(){

        Order order = OrderTestBuilder.defaultValues().build();

        assertThat(order).isEqualTo(repository.save(order));

    }

    @Test
    void findByIdShouldReturnEmptyOptionalIfIdIncorrect() {

        Optional<Order> maybeOrder = repository.findById(LAST_ENTITY_ID + 1);

        assertThat(maybeOrder).isNotPresent();
    }


    @Test
    void findByIdShouldReturnInstanceOfTag(){

        assertThat(repository.findById(LAST_ENTITY_ID).get())
                .isInstanceOf(Order.class);

    }


    @Test
    void findAllShouldReturnExpectedCountList(){

        Page<Order> all = repository.findAll(Pageable.unpaged());

        assertThat(all.getContent()).hasSize(LAST_ENTITY_ID.intValue() - 1);

    }

    @Test
    void findAllShouldReturnEmptyList(){

        Page<Order> all = repository.findAll(PageRequest.of(100, 5));

        assertThat(all.getContent())
                .isEmpty();

    }

    @Test
    void deleteShouldRemoveEntity(){

        repository.deleteById(LAST_ENTITY_ID);

        Optional<Order> maybeOrder = repository.findById(LAST_ENTITY_ID);

        assertThat(maybeOrder).isNotPresent();

    }

    @Test
    void existsByIdShouldReturnTrueIfEntityProvided(){

        assertThat(repository.existsById(LAST_ENTITY_ID))
                .isTrue();

    }

    @Test
    void existsByIdShouldReturnFalseIfEntityNotProvided(){

        assertThat(repository.existsById(LAST_ENTITY_ID + 1))
                .isFalse();

    }

    @Test
    void findAllByUserIdShouldReturnExpectedCountPage(){

        Page<Order> allByUserId = repository.findAllByUserId(Pageable.unpaged(), LAST_ENTITY_ID);

        assertThat(allByUserId.getTotalElements()).isEqualTo(1);

    }

    @Test
    void findAllByUserIdShouldReturnEmptyPageIfUserIdIncorrect(){

        Page<Order> allByUserId = repository.findAllByUserId(Pageable.unpaged(), LAST_ENTITY_ID + 1);

        assertThat(allByUserId).isEmpty();

    }

    @Test
    void findByUserIdAndIdShouldReturnInstanceOfOrder(){

        Optional<Order> byUserIdAndId = repository.findByUserIdAndId(LAST_ENTITY_ID, LAST_ENTITY_ID);

        assertThat(byUserIdAndId).isPresent();

    }

    @Test
    void findByUserIdAndIdShouldThrowExceptionIfIdIncorrect(){

        Optional<Order> byUserIdAndId = repository.findByUserIdAndId(LAST_ENTITY_ID + 1, LAST_ENTITY_ID + 1);

        assertThat(byUserIdAndId).isNotPresent();

    }

}