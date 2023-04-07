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
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.impl.GiftCertificateRepositoryImpl;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(SpringExtension.class)
class GiftCertificateRepositoryImplIntegrationTest {

    private GiftCertificateRepository repository;

    private EmbeddedDatabase dataSource;

    private static final Long COUNT_OF_CERTIFICATES = 4L;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/ddl.sql")
                .addScript("classpath:sql/dml.sql")
                .build();
        repository = new GiftCertificateRepositoryImpl(new JdbcTemplate(dataSource));
    }

    @Test
    @Disabled("RETURNING statement unavailable in H2")
    void createShouldReturnCreatedEntity(){
        GiftCertificate certificate = GiftCertificateTestBuilder.defaultValues().build();
        assertThat(certificate).isEqualTo(repository.create(certificate));
    }

    @Test
    void findByIdShouldThrowExceptionIfIdIncorrect(){
        assertThatThrownBy(() -> repository.findById(COUNT_OF_CERTIFICATES + 1)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void findByIdShouldReturnInstanceOfTag(){
        assertThat(repository.findById(1L).get()).isInstanceOf(GiftCertificate.class);
    }


    @Test
    void findAllShouldReturnExpectedCountList(){
        List<GiftCertificate> all = repository.findAllByPageableAndCertificateFilter(PageRequest.of(0, 5),
                                                                                     GiftCertificateFilter.defaultValues());
        assertThat(all).hasSize(COUNT_OF_CERTIFICATES.intValue());
    }

    @Test
    void findAllShouldReturnEmptyList(){
        List<GiftCertificate> all = repository.findAllByPageableAndCertificateFilter(PageRequest.of(100, 5),
                                                                                     GiftCertificateFilter.defaultValues());
        assertThat(all).isEmpty();
    }

    @Test
    void deleteShouldRemoveEntity(){
        repository.deleteById(1L);
        assertThat(repository.findAllByPageableAndCertificateFilter(PageRequest.of(0, 5), GiftCertificateFilter.defaultValues()))
                  .hasSize( COUNT_OF_CERTIFICATES.intValue() - 1);
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