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
import ru.clevertec.ecl.repository.api.IGiftCertificateRepository;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.utils.GiftCertificateTestBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@RunWith(SpringRunner.class)
class IGiftCertificateRepositoryImplTest {

    private IGiftCertificateRepository repository;

    private EmbeddedDatabase dataSource;

    private static final Long COUNT_OF_CERTIFICATES = 4L;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/ddl.sql")
                .addScript("classpath:sql/dml.sql")
                .build();
        repository = new IGiftCertificateRepositoryImpl(new JdbcTemplate(dataSource));
    }

    @Test
    @Disabled("RETURNING statement unavailable in H2")
    void createShouldReturnCreatedEntity(){
        GiftCertificate certificate = GiftCertificateTestBuilder.randomValues().build();
        assertThat(certificate).isEqualTo(repository.create(certificate));
    }

    @Test
    void getByIdShouldThrowExceptionIfIdIncorrect(){
        assertThatThrownBy(() -> repository.get(COUNT_OF_CERTIFICATES + 1)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getByIdShouldReturnInstanceOfTag(){
        assertThat(repository.get(1L)).isInstanceOf(GiftCertificate.class);
    }


    @Test
    void getAllShouldReturnExpectedCountList(){
        List<GiftCertificate> all = repository.getAll(FilteredPageRequest.of(1, 5));
        assertThat(all).hasSize(COUNT_OF_CERTIFICATES.intValue());
    }

    @Test
    void getAllShouldReturnEmptyList(){
        List<GiftCertificate> all = repository.getAll(FilteredPageRequest.of(100, 5));
        assertThat(all).isEmpty();
    }

    @Test
    void deleteShouldRemoveEntity(){
        repository.deleteById(1L);
        assertThat(repository.getAll(FilteredPageRequest.of(1, 5))).hasSize( COUNT_OF_CERTIFICATES.intValue() - 1);
    }

    @Test
    void addTagsAssociationShouldInsertIntoCrossTable(){
        List<Long> tagIds = LongStream.rangeClosed(1, COUNT_OF_CERTIFICATES)
                                        .boxed()
                                        .collect(Collectors.toList());

        repository.addTagsAssociation(1, tagIds);

        assertThat(repository.getAssociatedTags(1)).hasSize(tagIds.size());
    }

    @AfterEach
    void clear() {
        dataSource.shutdown();
    }
}