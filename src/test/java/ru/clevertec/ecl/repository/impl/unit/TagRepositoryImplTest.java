package ru.clevertec.ecl.repository.impl.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.impl.TagRepositoryImpl;
import ru.clevertec.ecl.service.util.GCRequestUtils;
import ru.clevertec.ecl.service.util.TagRequestUtils;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagRepositoryImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private TagRepositoryImpl repository;

    @Captor
    ArgumentCaptor<BeanPropertyRowMapper<Tag>> rowMapperCaptor;


    @Test
    void createShouldReturnCreatedEntity(){
        Tag tag = TagTestBuilder.defaultValues().build();
        //Cause i use "new" in implementation -> can't mock it
        doReturn(tag)
                .when(jdbcTemplate).queryForObject(eq(TagRequestUtils.CREATE_TAG_SQL), rowMapperCaptor.capture(),
                                                        eq(tag.getCreateDate()), eq(tag.getUpdateDate()),
                                                        eq(tag.getName()));
        assertThat(tag).isEqualTo(repository.create(tag));
    }

    @Test
    void findByIdShouldReturnEntity() {
        Tag tag = TagTestBuilder.defaultValues().build();
        doReturn(tag)
                .when(jdbcTemplate).queryForObject(eq(TagRequestUtils.GET_TAG_BY_ID_SQL), rowMapperCaptor.capture(), eq(tag.getId()));

        assertThat(tag).isEqualTo(repository.findById(tag.getId()).get());

    }

    @Test
    void findByIdShouldThrowExceptionIfIdIncorrect() {
        Tag tag = TagTestBuilder.defaultValues().build();
        Optional<Tag> tagOptional = Optional.empty();
        doReturn(null)
                .when(jdbcTemplate).queryForObject(eq(TagRequestUtils.GET_TAG_BY_ID_SQL), rowMapperCaptor.capture(), eq(tag.getId()));

        ;
        assertThat(repository.findById(tag.getId())).isNotPresent();

    }

    @Test
    void findAllByPageableAndCertificateFilterShouldReturnExpectedList() {
        Pageable pageable = PageRequest.of(0, 1);

        List<Tag> expected = List.of(TagTestBuilder.defaultValues().build());
        doReturn(expected)
                .when(jdbcTemplate).query(eq(TagRequestUtils.GET_ALL_TAGS_WITH_LIMIT_OFFSET_SQL), rowMapperCaptor.capture(), eq(pageable.getPageSize()), eq(pageable.getOffset()));


        assertThat(repository.findAll(pageable)).isEqualTo(expected);
    }

    @Test
    void updateShouldReturnEntityAndCallRepository() {
        Tag tag = TagTestBuilder.defaultValues().build();
        doReturn(tag)
                .when(jdbcTemplate).queryForObject(eq(TagRequestUtils.UPDATE_TAG_SQL), rowMapperCaptor.capture(),
                                                   eq(tag.getCreateDate()), eq(tag.getUpdateDate()),
                                                   eq(tag.getName()), eq(tag.getId()));

        assertThat(tag).isEqualTo(repository.update(tag));
        verify(jdbcTemplate).queryForObject(eq(TagRequestUtils.UPDATE_TAG_SQL), rowMapperCaptor.capture(),
                                            eq(tag.getCreateDate()), eq(tag.getUpdateDate()),
                                            eq(tag.getName()), eq(tag.getId()));

    }

    @Test
    void deleteByIdShouldCallJdbcTemplate() {
        repository.deleteById(1L);
        verify(jdbcTemplate).update(TagRequestUtils.DELETE_TAG_BY_ID, 1L);
    }
}