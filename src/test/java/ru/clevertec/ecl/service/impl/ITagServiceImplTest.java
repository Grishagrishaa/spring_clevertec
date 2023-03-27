package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.controler.pagination.FilteredPageRequest;
import ru.clevertec.ecl.controler.pagination.Pageable;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.mappers.api.ITagMapper;
import ru.clevertec.ecl.utils.TagTestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ITagServiceImplTest {

    @Spy
    private ITagMapper tagMapper;
    @Mock
    private ITagRepository tagRepository;

    @InjectMocks
    private ITagServiceImpl service;

    private static final Long ID = 9L;



    @ParameterizedTest
    @MethodSource("provideTag")
    void getAllShouldReturnReadDto(Tag tag) {
        Pageable pageable = FilteredPageRequest.of(0, 2);

        doReturn(Collections.singletonList(tag)).when(tagRepository).getAll(pageable);

        List<TagReadDto> expected = Collections.singletonList(TagTestBuilder.readDto(tag));
        List<TagReadDto> actual = service.getAll(pageable);

        verify(tagRepository).getAll(pageable);
        assertThat(actual).isEqualTo(expected);
    }
    @ParameterizedTest
    @MethodSource("provideTag")
    void getShouldReturnReadDto(Tag tag) {
        TagReadDto expected = TagTestBuilder.readDto(tag);
        doReturn(tag).when(tagRepository).get(tag.getId());

        TagReadDto actual = service.get(tag.getId());

        verify(tagRepository).get(tag.getId());
        assertThat(actual).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("provideTag")
    void createShouldReturnReadDto(Tag tag) {
        TagCreateDto createDto = TagTestBuilder.createDto(tag);
        TagReadDto expected = TagTestBuilder.readDto(tag);

        doReturn(tag).when(tagRepository).create(any());

        TagReadDto actual = service.create(createDto);

        assertThat(actual).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("provideTag")
    void updateShouldCallRepository2Times(Tag tag) {
        doReturn(tag).when(tagRepository).get(tag.getId());
        doReturn(tag).when(tagRepository).update(tag);

        TagCreateDto createDto = TagTestBuilder.createDto(tag);

        service.update(createDto, tag.getId(), tag.getUpdateDate());

        verify(tagRepository).get(tag.getId());
        verify(tagRepository).update(tag);
    }


    @Test
    void checkDeleteByIdShouldCallRepository() {
        doNothing().when(tagRepository).deleteById(ID);
        tagRepository.deleteById(ID);

        verify(tagRepository).deleteById(ID);
    }


    private static Stream<Arguments> provideTag() {
        return Stream.of(
                Arguments.of(TagTestBuilder.randomValues().build()),
                Arguments.of(TagTestBuilder.randomValues().build()),
                Arguments.of(TagTestBuilder.randomValues().build()),
                Arguments.of(TagTestBuilder.randomValues().build()),
                Arguments.of(TagTestBuilder.randomValues().build())
        );
    }

}