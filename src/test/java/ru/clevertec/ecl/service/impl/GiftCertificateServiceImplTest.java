package ru.clevertec.ecl.service.impl;

import jakarta.persistence.OptimisticLockException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.mappers.api.IGiftCertificateMapper;
import ru.clevertec.ecl.service.mappers.api.ITagMapper;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Spy
    private IGiftCertificateMapper certificateMapper;
    @Spy
    private ITagMapper tagMapper;

    @Mock
    private GiftCertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateServiceImpl service;

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
    void findByIdShouldReturnReadDto(GiftCertificate certificate, Tag tag) {
        TagReadDto tagReadDto = TagTestBuilder.readDto(tag);

        doReturn(certificate).when(certificateRepository).findById(ID);
        doReturn(Collections.singletonList(tag)).when(certificateRepository).getAssociatedTags(ID);


        List<TagReadDto> expectedTags = Collections.singletonList(tagReadDto);
        GiftCertificateReadDto expectedCertificate = GiftCertificateTestBuilder.readDto(certificate);
        expectedCertificate.setTags(expectedTags);

        GiftCertificateReadDto actual = service.findById(ID);

        verify(certificateRepository).findById(ID);
        verify(certificateRepository).getAssociatedTags(ID);
        assertThat(actual).isEqualTo(expectedCertificate);
    }


    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void findAllPageableAndFilterShouldReturnExpectedList(GiftCertificate certificate, Tag tag) {
        Pageable pageable = PageRequest.of(0, 1);

        GiftCertificateReadDto readDto = GiftCertificateTestBuilder.readDto(certificate);
        List<TagReadDto> expectedTags = List.of(TagTestBuilder.readDto(tag));
        readDto.setTags(expectedTags);
        List<GiftCertificateReadDto> expected = List.of(readDto);

        doReturn(Collections.singletonList(certificate)).when(certificateRepository).findAllByPageableAndCertificateFilter(pageable, GiftCertificateFilter.defaultValues());
        doReturn(Collections.singletonList(tag)).when(certificateRepository).getAssociatedTags(anyLong());

        List<GiftCertificateReadDto> actual = service.findAllByGiftCertificateFilter(pageable, GiftCertificateFilter.defaultValues());

        verify(certificateRepository).findAllByPageableAndCertificateFilter(pageable, GiftCertificateFilter.defaultValues());
        verify(certificateRepository).getAssociatedTags(anyLong());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdShouldThrowExceptionIfNoEntityProvided() {
        doThrow(EmptyResultDataAccessException.class).when(certificateRepository).findById(ID);
        assertThatThrownBy(() -> service.findById(ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }


    @Disabled("VERSION implementation removed")
    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void updateByIdShouldThrowExceptionIfVersionIncorrect(GiftCertificate certificate, Tag tag) {
        GiftCertificateCreateDto createDto = GiftCertificateTestBuilder.createDto(certificate);
        createDto.setTags(Collections.singletonList(TagTestBuilder.createDto(tag)));

        doReturn(certificate).when(certificateRepository).findById(certificate.getId());

        assertThatThrownBy(() -> service.update(createDto, certificate.getId()))
                .isInstanceOf(OptimisticLockException.class);
    }

    @Test
    void deleteByIdShouldCallRepository() {
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
