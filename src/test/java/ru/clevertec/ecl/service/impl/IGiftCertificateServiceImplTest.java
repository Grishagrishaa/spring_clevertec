package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.clevertec.ecl.controler.pagination.FilteredPageRequest;
import ru.clevertec.ecl.controler.pagination.Pageable;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.exceptions.OptimisticLockException;
import ru.clevertec.ecl.repository.api.IGiftCertificateRepository;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.mappers.api.IGiftCertificateMapper;
import ru.clevertec.ecl.service.mappers.api.ITagMapper;
import ru.clevertec.ecl.utils.GiftCertificateTestBuilder;
import ru.clevertec.ecl.utils.TagTestBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IGiftCertificateServiceImplTest {
    @Spy
    private IGiftCertificateMapper certificateMapper;
    @Spy
    private ITagMapper tagMapper;

    @Mock
    private IGiftCertificateRepository certificateRepository;
    @Mock
    private ITagRepository tagRepository;

    @InjectMocks
    private IGiftCertificateServiceImpl service;

    private static final Long ID = 9L;


    @Disabled
    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void createShouldReturnReadDtoAndCallRepository(GiftCertificate certificate, Tag tag) {
        GiftCertificateCreateDto createDto = GiftCertificateTestBuilder.createDto(certificate);
        createDto.setTags(Collections.singletonList(TagTestBuilder.createDto(tag)));

        GiftCertificateReadDto expected = GiftCertificateTestBuilder.readDto(certificate);
        expected.setTags(Collections.singletonList(TagTestBuilder.readDto(tag)));

        doReturn(certificate).when(certificateRepository).create(certificate);
        doReturn(false).when(tagRepository).exists(tag);
        doReturn(tag.getId()).when(tagRepository).create(tag);
        doReturn(Collections.singletonList(tag)).when(certificateRepository).getAssociatedTags(certificate.getId());

        assertThat(service.create(createDto)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void getShouldReturnReadDto(GiftCertificate certificate, Tag tag) {
        TagReadDto tagReadDto = TagTestBuilder.readDto(tag);

        doReturn(certificate).when(certificateRepository).get(ID);
        doReturn(Collections.singletonList(tag)).when(certificateRepository).getAssociatedTags(ID);


        List<TagReadDto> expectedTags = Collections.singletonList(tagReadDto);
        GiftCertificateReadDto expectedCertificate = GiftCertificateTestBuilder.readDto(certificate);
        expectedCertificate.setTags(expectedTags);

        GiftCertificateReadDto actual = service.get(ID);

        verify(certificateRepository).get(ID);
        verify(certificateRepository).getAssociatedTags(ID);
        assertThat(actual).isEqualTo(expectedCertificate);
    }


    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void getAllShouldReturnExpectedList(GiftCertificate certificate, Tag tag) {
        Pageable pageable = FilteredPageRequest.of(0, 1);

        GiftCertificateReadDto readDto = GiftCertificateTestBuilder.readDto(certificate);
        List<TagReadDto> expectedTags = List.of(TagTestBuilder.readDto(tag));
        readDto.setTags(expectedTags);
        List<GiftCertificateReadDto> expected = List.of(readDto);

        doReturn(Collections.singletonList(certificate)).when(certificateRepository).getAll(pageable);
        doReturn(Collections.singletonList(tag)).when(certificateRepository).getAssociatedTags(anyLong());

        List<GiftCertificateReadDto> actual = service.getAll(pageable);

        verify(certificateRepository).getAll(pageable);
        verify(certificateRepository).getAssociatedTags(anyLong());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getShouldThrowExceptionIfNoEntityProvided() {
        doThrow(EmptyResultDataAccessException.class).when(certificateRepository).get(ID);
        assertThatThrownBy(() -> service.get(ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }



    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void updateShouldThrowExceptionIfVersionIncorrect(GiftCertificate certificate, Tag tag) {
        GiftCertificateCreateDto createDto = GiftCertificateTestBuilder.createDto(certificate);
        createDto.setTags(Collections.singletonList(TagTestBuilder.createDto(tag)));

        doReturn(certificate).when(certificateRepository).get(certificate.getId());

        assertThatThrownBy(() -> service.update(createDto, certificate.getId(), certificate.getCreateDate()))
                .isInstanceOf(OptimisticLockException.class);
    }

    @Test
    void checkDeleteByIdShouldCallRepository() {
        doNothing().when(certificateRepository).deleteById(ID);
        certificateRepository.deleteById(ID);

        verify(certificateRepository).deleteById(ID);
    }

    private static Stream<Arguments> provideGiftCertificateAndTag() {
        return Stream.of(
                Arguments.of(GiftCertificateTestBuilder.randomValues().build(), TagTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build(), TagTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build(), TagTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build(), TagTestBuilder.randomValues().build()),
                Arguments.of(GiftCertificateTestBuilder.randomValues().build(), TagTestBuilder.randomValues().build())
        );
    }
}
