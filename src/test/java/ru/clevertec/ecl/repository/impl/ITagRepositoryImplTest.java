package ru.clevertec.ecl.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.clevertec.ecl.controler.pagination.FilteredPageRequest;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.utils.TagTestBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@RunWith(SpringRunner.class)
class ITagRepositoryImplTest {

    private ITagRepository repository;

    private EmbeddedDatabase dataSource;

    private static final Long COUNT_OF_TAGS = 4L;
    private static final Tag TAG_FROM_DML = Tag.builder()
            .id(1L)
            .name("tag_name1")
            .createDate(LocalDateTime.parse("2022-12-21T18:56:01.000000"))
            .updateDate(LocalDateTime.parse("2022-12-21T18:56:01.000000"))
            .build();

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/ddl.sql")
                .addScript("classpath:sql/dml.sql")
                .build();
        repository = new ITagRepositoryImpl(new JdbcTemplate(dataSource));
    }

    @Test
    @Disabled("RETURNING statement unavailable in H2")
    void createShouldReturnCreatedEntity(){
        Tag tag = TagTestBuilder.randomValues().build();
        assertThat(tag).isEqualTo(repository.create(tag));
    }

    @Test
    void getByIdShouldThrowExceptionIfIdIncorrect(){
        assertThatThrownBy(() -> repository.get(COUNT_OF_TAGS + 1)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getByIdShouldReturnInstanceOfTag(){
        assertThat(repository.get(1L)).isInstanceOf(Tag.class);
    }


    @Test
    void getAllShouldReturnExpectedCountList(){
        List<Tag> all = repository.getAll(FilteredPageRequest.of(1, 5));
        assertThat(all).hasSize(COUNT_OF_TAGS.intValue());
    }

    @Test
    void getAllShouldReturnEmptyList(){
        List<Tag> all = repository.getAll(FilteredPageRequest.of(100, 5));
        assertThat(all).isEmpty();
    }

    @Test
    void deleteShouldRemoveEntity(){
        repository.deleteById(1L);
        assertThat(repository.getAll(FilteredPageRequest.of(1, 5))).hasSize( COUNT_OF_TAGS.intValue() - 1);
    }

    @Test
    void existsShouldReturnTrueIfEntityProvided(){
        assertThat(repository.exists(TAG_FROM_DML)).isTrue();
    }

    @Test
    void existsShouldReturnFalseIfEntityNotProvided(){
        assertThat(repository.exists(TagTestBuilder.randomValues().build())).isFalse();
    }

    @AfterEach
    void clear() {
        dataSource.shutdown();
    }
}