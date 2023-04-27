package ru.clevertec.ecl.integration.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.impl.TagServiceImpl;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RequiredArgsConstructor
class TagServiceImplTest extends BaseIntegrationTest {

    private final TagServiceImpl service;


    @Test
    void createShouldReturnReadDto() {
        Tag tag = TagTestBuilder.defaultValues().build();

        TagReadDto actual = service.create(TagTestBuilder.createDto(tag));

        assertThat(actual).isInstanceOf(TagReadDto.class);

    }


    @Test
    void findAllByPageableShouldReturnExpectedCountOfElements() {

        Page<TagReadDto> actual = service.findAll(Pageable.unpaged());

        assertThat(actual.getTotalElements()).isEqualTo(LAST_ENTITY_ID);

    }

    @Test
    void findByIdShouldReturnReadDtoInstance() {

        TagReadDto actual = service.findById(LAST_ENTITY_ID);

        assertThat(actual).isInstanceOf(TagReadDto.class);

    }

    @Test
    void findByIdShouldThrowExceptionIfNoEntityProvided() {

        assertThatThrownBy(() -> service.findById(LAST_ENTITY_ID + 1))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void findMostPopularWithHighestCostByUserIdShouldReturnReadDtoInstance(){

        TagReadDto actual = service.findMostPopularWithHighestCostByUserId(LAST_ENTITY_ID);

        assertThat(actual).isInstanceOf(TagReadDto.class);

    }

    @Test
    void findMostPopularWithHighestCostByUserIdShouldThrwExcIfIdInvalid(){

        assertThatThrownBy(() -> service.findMostPopularWithHighestCostByUserId(LAST_ENTITY_ID + 1))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void updateByIdShouldReturnUpdatedReadDto() {
        Tag tag = TagTestBuilder.defaultValues().build();

        TagReadDto toUpdate = TagTestBuilder.readDto(tag);
        TagReadDto notUpdated = service.findById(LAST_ENTITY_ID);

        TagReadDto updated = service.update(TagTestBuilder.createDto(tag), LAST_ENTITY_ID);

        assertAll(
                () -> assertThat(notUpdated).isNotEqualTo(toUpdate),
                () -> assertThat(updated).isEqualTo(toUpdate)
        );

    }

    @Test
    void deleteShouldRemoveEntity() {
        TagCreateDto createDto = TagTestBuilder.createDto(TagTestBuilder.defaultValues().build());

        TagReadDto created = service.create(createDto);

        service.deleteById(created.getId());

        assertThatThrownBy(() -> service.findById(created.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

}