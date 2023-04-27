package ru.clevertec.ecl.integration.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.service.impl.GiftCertificateServiceImpl;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RequiredArgsConstructor
class GiftCertificateServiceImplTest extends BaseIntegrationTest {

    private final GiftCertificateServiceImpl service;

    private static final Long LAST_ENTITY_ID = 9L;


    @Test
    void createShouldReturnReadDto() {

        GiftCertificate certificate = GiftCertificateTestBuilder.defaultValues().build();

        GiftCertificateCreateDto createDto = GiftCertificateTestBuilder.createDto(certificate);
        GiftCertificateReadDto expected = GiftCertificateTestBuilder.readDto(certificate);

        assertThat(service.create(createDto))
                .isEqualTo(expected);

    }

    @Test
    void findByIdShouldReturnReadDtoInstance() {

        GiftCertificateReadDto actual = service.findById(LAST_ENTITY_ID);

        assertThat(actual)
                .isInstanceOf(GiftCertificateReadDto.class);

    }

    @Test
    void findByIdShouldReturnReadDtoWithTags() {

        GiftCertificateReadDto actual = service.findById(LAST_ENTITY_ID);

        assertThat(actual.getTags())
                .isNotEmpty();

    }

    @Test
    void findByIdShouldThrowExceptionIfNoEntityProvided() {

        assertThatThrownBy(() -> service.findById(LAST_ENTITY_ID + 1))
                .isInstanceOf(EntityNotFoundException.class);

    }


    @Test
    void findAllPageableAndFilterShouldReturnExpectedList() {

        Page<GiftCertificateReadDto> actual = service.findAllByGiftCertificateFilter(Pageable.unpaged(), GiftCertificateFilter.defaultValues());

        assertThat(actual.getContent())
                .hasSize(9);

    }


    @Test
    void updateByIdShouldReturnUpdatedReadDto() {
        GiftCertificate certificate = GiftCertificateTestBuilder.defaultValues().build();

        GiftCertificateReadDto toUpdate = GiftCertificateTestBuilder.readDto(certificate);
        GiftCertificateReadDto notUpdated = service.findById(LAST_ENTITY_ID);

        GiftCertificateReadDto updated = service.update(GiftCertificateTestBuilder.createDto(certificate), LAST_ENTITY_ID);

        assertAll(
                () -> assertThat(notUpdated).isNotEqualTo(toUpdate),
                () -> assertThat(updated).isEqualTo(toUpdate)
        );
        
    }

    @Test
    void deleteShouldRemoveEntity() {
        GiftCertificateCreateDto createDto = GiftCertificateTestBuilder.createDto(GiftCertificateTestBuilder.defaultValues().build());

        GiftCertificateReadDto created = service.create(createDto);

        service.deleteById(created.getId());

        assertThatThrownBy(() -> service.findById(created.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

}