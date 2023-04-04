package ru.clevertec.ecl.service.mappers.api;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.service.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.service.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class GiftCertificateMapperTest {
    private final GiftCertificateMapper mapper = Mappers.getMapper(GiftCertificateMapper.class);


    @ParameterizedTest
    @MethodSource("provideGiftCertificateCreateDto")
    void createDtoToEntity(GiftCertificateCreateDto giftCertificate) {
        GiftCertificate entity = mapper.createDtoToEntity(giftCertificate);
        assertAll(
                () -> assertThat(entity.getName()).isEqualTo(giftCertificate.getName()),
                () -> assertThat(entity.getDescription()).isEqualTo(giftCertificate.getDescription()),
                () -> assertThat(entity.getPrice()).isEqualTo(giftCertificate.getPrice()),
                () -> assertThat(entity.getDuration()).isEqualTo(giftCertificate.getDuration())
        );
    }

    @ParameterizedTest
    @MethodSource("provideGiftCertificate")
    void entityToReadDto(GiftCertificate giftCertificate) {
        GiftCertificateReadDto readDto = mapper.entityToReadDto(giftCertificate);
        assertAll(
                () -> assertThat(readDto.getId()).isEqualTo(giftCertificate.getId()),
                () -> assertThat(readDto.getName()).isEqualTo(giftCertificate.getName()),
                () -> assertThat(readDto.getDescription()).isEqualTo(giftCertificate.getDescription()),
                () -> assertThat(readDto.getPrice()).isEqualTo(giftCertificate.getPrice()),
                () -> assertThat(readDto.getDuration()).isEqualTo(giftCertificate.getDuration())
        );
    }

    private static Stream<Arguments> provideGiftCertificate() {
        return Stream.of(
                Arguments.of(GiftCertificateTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build())
        );
    }

    private static Stream<Arguments> provideGiftCertificateCreateDto() {
        return Stream.of(
                Arguments.of(GiftCertificateTestBuilder.createDto(GiftCertificateTestBuilder.randomValues().build())),
                Arguments.of(GiftCertificateTestBuilder.createDto(GiftCertificateTestBuilder.randomValues().build())),
                Arguments.of(GiftCertificateTestBuilder.createDto(GiftCertificateTestBuilder.randomValues().build())),
                Arguments.of(GiftCertificateTestBuilder.createDto(GiftCertificateTestBuilder.randomValues().build())),
                Arguments.of(GiftCertificateTestBuilder.createDto(GiftCertificateTestBuilder.randomValues().build()))
        );
    }
}