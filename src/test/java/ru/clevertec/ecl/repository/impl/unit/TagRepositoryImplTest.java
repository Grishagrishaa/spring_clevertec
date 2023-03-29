package ru.clevertec.ecl.repository.impl.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.impl.TagRepositoryImpl;
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

    @Test
    void createShouldReturnCreatedEntity(){
        Tag tag = TagTestBuilder.randomValues().build();
        //Cause i use "new" in implementation -> can't mock it
        doReturn(tag).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class),
                                                        any(), any(),
                                                        any());
        assertThat(tag).isEqualTo(repository.create(tag));
    }

    @Test
    void findByIdShouldReturnEntity() {
        Tag tag = TagTestBuilder.randomValues().build();
        doReturn(tag).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyLong());

        assertThat(tag).isEqualTo(repository.findById(tag.getId()).get());

    }

    @Test
    void findByIdShouldThrowExceptionIfIdIncorrect() {
        Tag tag = TagTestBuilder.randomValues().build();
        Optional<Tag> tagOptional = Optional.empty();
        doReturn(null).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyLong());

        ;
        assertThat(repository.findById(tag.getId())).isNotPresent();

    }

    @Test
    void findAllByPageableAndCertificateFilterShouldReturnExpectedList() {
        Pageable pageable = PageRequest.of(0, 1);

        List<Tag> expected = List.of(TagTestBuilder.randomValues().build());
        doReturn(expected)
                .when(jdbcTemplate).query(anyString(), any(BeanPropertyRowMapper.class), anyInt(), anyLong());


        assertThat(repository.findAll(pageable)).isEqualTo(expected);
    }

    @Test
    void updateShouldReturnEntityAndCallRepository() {
        Tag tag = TagTestBuilder.randomValues().build();
        doReturn(tag).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class),
                                                        any(), any(),
                                                        anyString(), anyLong());

        assertThat(tag).isEqualTo(repository.update(tag));
        verify(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class),
                                            any(), any(),
                                            anyString(), anyLong());
    }

    @Test
    void deleteByIdShouldCallJdbcTemplate() {
        repository.deleteById(1L);
        verify(jdbcTemplate).update(TagRequestUtils.DELETE_TAG_BY_ID, 1L);
    }
}