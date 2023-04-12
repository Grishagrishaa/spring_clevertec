package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.service.mappers.api.GiftCertificateMapper;
import ru.clevertec.ecl.service.mappers.api.TagMapper;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Spy
    private GiftCertificateMapper certificateMapper = Mappers.getMapper(GiftCertificateMapper.class);;
    @Spy
    private TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    @Mock
    private GiftCertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateServiceImpl service;

    private static final Long ID = 9L;


    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void createShouldReturnReadDtoAndCallRepository(GiftCertificate certificate, Tag tag) {
        GiftCertificateCreateDto createDto = GiftCertificateTestBuilder.createDto(certificate);
        createDto.setTags(List.of(TagTestBuilder.createDto(tag)));

        GiftCertificateReadDto expected = GiftCertificateTestBuilder.readDto(certificate);
        expected.setTags(List.of(TagTestBuilder.readDto(tag)));

        doReturn(certificate).when(certificateRepository).save(certificate);
        doReturn(Optional.empty()).when(tagRepository).findByName(tag.getName());
        lenient().doReturn(tag).when(tagRepository).save(tag);

        assertThat(service.create(createDto)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void findByIdShouldReturnReadDto(GiftCertificate certificate, Tag tag) {
        TagReadDto tagReadDto = TagTestBuilder.readDto(tag);
        List<TagReadDto> expectedTags = List.of(tagReadDto);

        certificate.setTags(List.of(tag));
        doReturn(Optional.ofNullable(certificate)).when(certificateRepository).findById(ID);

        GiftCertificateReadDto expectedCertificate = GiftCertificateTestBuilder.readDto(certificate);
        expectedCertificate.setTags(expectedTags);

        doReturn(expectedCertificate).when(certificateMapper).entityToReadDto(certificate);

        GiftCertificateReadDto actual = service.findById(ID);

        verify(certificateRepository).findById(ID);
        assertThat(actual).isEqualTo(expectedCertificate);
    }


    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void findAllPageableAndFilterShouldReturnExpectedList(GiftCertificate certificate, Tag tag) {
        certificate.setTags(List.of(tag));
        Pageable pageable = PageRequest.of(0, 1);

        GiftCertificateReadDto readDto = GiftCertificateTestBuilder.readDto(certificate);
        List<TagReadDto> expectedTags = List.of(TagTestBuilder.readDto(tag));
        readDto.setTags(expectedTags);
        Page<GiftCertificateReadDto> expected = new PageImpl<>(List.of(readDto));

        doReturn(readDto).when(certificateMapper).entityToReadDto(certificate);
        doReturn(new PageImpl<>(List.of(certificate))).when(certificateRepository).findAll(any(Specification.class), any(Pageable.class));

        Page<GiftCertificateReadDto> actual = service.findAllByGiftCertificateFilter(pageable, GiftCertificateFilter.defaultValues());

        verify(certificateRepository).findAll(any(Specification.class), any(Pageable.class));
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    void findByIdShouldThrowExceptionIfNoEntityProvided() {
        doThrow(EmptyResultDataAccessException.class).when(certificateRepository).findById(ID);
        assertThatThrownBy(() -> service.findById(ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }


    @ParameterizedTest
    @MethodSource("provideGiftCertificateAndTag")
    void updateByIdShouldCall2Repositories(GiftCertificate certificate, Tag tag) {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);
        certificate.setTags(tags);
        GiftCertificateCreateDto createDto = GiftCertificateTestBuilder.createDto(certificate);
        createDto.setTags(List.of(TagTestBuilder.createDto(tag)));

        doReturn(Optional.of(certificate)).when(certificateRepository).findById(certificate.getId());

        service.update(GiftCertificateTestBuilder.createDto(certificate), certificate.getId());

        verify(certificateRepository).save(certificate);
        verify(tagRepository).findByName(tag.getName());
    }

    @Test
    void deleteShouldCallRepository() {
        GiftCertificate certificate = new GiftCertificate();

        doReturn(Optional.of(certificate)).when(certificateRepository).findById(ID);
        service.deleteById(ID);

        verify(certificateRepository).delete(certificate);
    }

    private static Stream<Arguments> provideGiftCertificateAndTag() {
        return Stream.of(
                Arguments.of(GiftCertificateTestBuilder.defaultValues().build(), TagTestBuilder.defaultValues().build()),
                Arguments.of(GiftCertificateTestBuilder.defaultValues().build(), TagTestBuilder.defaultValues().build()),
                Arguments.of(GiftCertificateTestBuilder.defaultValues().build(), TagTestBuilder.defaultValues().build()),
                Arguments.of(GiftCertificateTestBuilder.defaultValues().build(), TagTestBuilder.defaultValues().build()),
                Arguments.of(GiftCertificateTestBuilder.defaultValues().build(), TagTestBuilder.defaultValues().build())
        );
    }
}