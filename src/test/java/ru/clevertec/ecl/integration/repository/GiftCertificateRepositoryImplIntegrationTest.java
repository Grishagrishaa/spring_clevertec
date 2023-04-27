package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
class GiftCertificateRepositoryImplIntegrationTest extends BaseIntegrationTest {

    private final GiftCertificateRepository repository;

    private static final Long LAST_ENTITY_ID = 9L;

    @Test
    void createShouldReturnCreatedEntity(){

        GiftCertificate certificate = GiftCertificateTestBuilder.defaultValues().build();

        assertThat(certificate).isEqualTo(repository.save(certificate));

    }

    @Test
    void findByIdShouldReturnEmptyOptionalIfIdIncorrect(){

        Optional<GiftCertificate> maybeCertificate = repository.findById(LAST_ENTITY_ID + 1);

        assertThat(maybeCertificate).isNotPresent();

    }

    @Test
    void findByIdShouldReturnInstanceOfCertificate(){

        assertThat(repository.findById(LAST_ENTITY_ID).get())
                .isInstanceOf(GiftCertificate.class);

    }


    @Test
    void findAllShouldReturnExpectedCountContentPage(){

        Page<GiftCertificate> all = repository.findAll(Pageable.unpaged());

        assertThat(all.getContent()).hasSize(LAST_ENTITY_ID.intValue());

    }

    @Test
    void findAllShouldReturnEmptyPage(){

        Page<GiftCertificate> all = repository.findAll(PageRequest.of(100, 5));

        assertThat(all.getContent()).isEmpty();

    }

    @Test
    void deleteShouldRemoveEntity(){

        repository.deleteById(LAST_ENTITY_ID);

        Optional<GiftCertificate> maybeCertificate = repository.findById(LAST_ENTITY_ID);

        assertThat(maybeCertificate).isNotPresent();

    }

}