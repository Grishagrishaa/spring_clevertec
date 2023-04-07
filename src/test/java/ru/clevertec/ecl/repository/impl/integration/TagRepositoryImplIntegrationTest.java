package ru.clevertec.ecl.repository.impl.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.impl.TagRepositoryImpl;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
class TagRepositoryImplIntegrationTest {
    private TagRepository repository;

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
                .addScript(Path.of("classpath:sql",  "ddl.sql").toString())
                .addScript(Path.of("classpath:sql",  "dml.sql").toString())
                .build();
        repository = new TagRepositoryImpl(new JdbcTemplate(dataSource));
    }

    @Test
    @Disabled("RETURNING statement unavailable in H2")
    void createShouldReturnCreatedEntity(){
        Tag tag = TagTestBuilder.defaultValues().build();
        assertThat(tag).isEqualTo(repository.create(tag));
    }

    @Test
    void findByIdShouldThrowExceptionIfIdIncorrect(){
        assertThatThrownBy(() -> repository.findById(COUNT_OF_TAGS + 1)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void findByIdShouldReturnInstanceOfTag(){
        assertThat(repository.findById(1L).get()).isInstanceOf(Tag.class);
    }


    @Test
    void findAllShouldReturnExpectedCountList(){
        List<Tag> all = repository.findAll(PageRequest.of(0, 5));
        assertThat(all).hasSize(COUNT_OF_TAGS.intValue());
    }

    @Test
    void findAllShouldReturnEmptyList(){
        List<Tag> all = repository.findAll(PageRequest.of(100, 5));
        assertThat(all).isEmpty();
    }

    @Test
    void deleteShouldRemoveEntity(){
        repository.deleteById(1L);
        assertThat(repository.findAll(PageRequest.of(0, 5))).hasSize( COUNT_OF_TAGS.intValue() - 1);
    }

    @Test
    void existsShouldReturnTrueIfEntityProvided(){
        assertThat(repository.exists(TAG_FROM_DML)).isTrue();
    }

    @Test
    void existsShouldReturnFalseIfEntityNotProvided(){
        assertThat(repository.exists(TagTestBuilder.defaultValues().build())).isFalse();
    }

    @AfterEach
    void clear() {
        dataSource.shutdown();
    }
}