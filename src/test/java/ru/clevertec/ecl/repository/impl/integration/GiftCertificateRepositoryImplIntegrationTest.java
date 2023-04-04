package ru.clevertec.ecl.repository.impl.integration;


import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.config.HibernateTestConf;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.impl.GiftCertificateRepositoryImpl;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Transactional
@Rollback
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateTestConf.class)
class GiftCertificateRepositoryImplIntegrationTest {

    @Autowired
    private SessionFactory testSessionFactory;

    private final GiftCertificateRepository repository = new GiftCertificateRepositoryImpl(testSessionFactory);

    private static final Long COUNT_OF_CERTIFICATES = 4L;


    @Test
    void createShouldReturnCreatedEntity(){
        GiftCertificate certificate = GiftCertificateTestBuilder.randomValues().build();
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
        GiftCertificate certificate = new GiftCertificate("test1", "desc1", 20.2, 3, null);
        certificate.setCreateDate(Instant.parse("1680639466.704000000"));
        certificate.setUpdateDate(Instant.parse("1680639466.704000000"));
        certificate.setId(1L);//entity from dml

        repository.delete(certificate);
        assertThat(repository.findAllByPageableAndCertificateFilter(PageRequest.of(0, 5), GiftCertificateFilter.defaultValues()))
                  .hasSize( COUNT_OF_CERTIFICATES.intValue() - 1);
    }
}