package ru.clevertec.ecl.integration.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.read.UserReadDto;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.impl.UserServiceImpl;
import ru.clevertec.ecl.testUtils.builder.impl.UserTestBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RequiredArgsConstructor
class UserServiceImplTest extends BaseIntegrationTest {

    private final UserServiceImpl service;

    @Test
    void findAllByPageableShouldReturnExpectedCountOfEntities() {

        Page<UserReadDto> allByPageable = service.findAllByPageable(Pageable.unpaged());

        assertThat(allByPageable.getTotalElements()).isEqualTo(LAST_ENTITY_ID);

    }

    @Test
    void findByIdShouldReturnInstanceOfReadDto() {

        UserReadDto actual = service.findById(LAST_ENTITY_ID);

        assertThat(actual).isInstanceOf(UserReadDto.class);

    }

    @Test
    void findByIdShouldThrowExceptionIfIdInvalid() {

        assertThatThrownBy(() -> service.findById(LAST_ENTITY_ID + 1))
                .isInstanceOf(EntityNotFoundException.class);

    }

}