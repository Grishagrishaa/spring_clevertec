package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
//
//    @Spy
//    private ITagMapper tagMapper;
//    @Mock
//    private TagRepository tagRepository;
//
//    @InjectMocks
//    private TagServiceImpl service;
//
//    private static final Long ID = 9L;
//
//
//    @ParameterizedTest
//    @MethodSource("provideTag")
//    void createShouldReturnReadDto(Tag tag) {
//        TagCreateDto createDto = TagTestBuilder.createDto(tag);
//        TagReadDto expected = TagTestBuilder.readDto(tag);
//
//        doReturn(tag).when(tagRepository).create(any());
//
//        TagReadDto actual = service.create(createDto);
//
//        assertThat(actual).isEqualTo(expected);
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("provideTag")
//    void findAllByPageableShouldReturnReadDto(Tag tag) {
//        Pageable pageable = PageRequest.of(0, 2);
//
//        doReturn(Collections.singletonList(tag)).when(tagRepository).findAll(pageable);
//
//        List<TagReadDto> expected = Collections.singletonList(TagTestBuilder.readDto(tag));
//        List<TagReadDto> actual = service.findAll(pageable);
//
//        verify(tagRepository).findAll(pageable);
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideTag")
//    void findByIdShouldReturnReadDto(Tag tag) {
//        TagReadDto expected = TagTestBuilder.readDto(tag);
//        doReturn(tag).when(tagRepository).findById(tag.getId());
//
//        TagReadDto actual = service.findById(tag.getId());
//
//        verify(tagRepository).findById(tag.getId());
//        assertThat(actual).isEqualTo(expected);
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("provideTag")
//    void updateByIdShouldCallRepository2Times(Tag tag) {
//        doReturn(tag).when(tagRepository).findById(tag.getId());
//        doReturn(tag).when(tagRepository).update(tag);
//
//        TagCreateDto createDto = TagTestBuilder.createDto(tag);
//
//        service.update(createDto, tag.getId());
//
//        verify(tagRepository).findById(tag.getId());
//        verify(tagRepository).update(tag);
//    }
//
//
//    @Test
//    void deleteByIdShouldCallRepository() {
//        doNothing().when(tagRepository).delete(ID);
//        tagRepository.delete(ID);
//
//        verify(tagRepository).delete(ID);
//    }
//
//
//    private static Stream<Arguments> provideTag() {
//        return Stream.of(
//                Arguments.of(TagTestBuilder.randomValues().build()),
//                Arguments.of(TagTestBuilder.randomValues().build()),
//                Arguments.of(TagTestBuilder.randomValues().build()),
//                Arguments.of(TagTestBuilder.randomValues().build()),
//                Arguments.of(TagTestBuilder.randomValues().build())
//        );
//    }

}