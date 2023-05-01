package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class TagRepositoryImplIntegrationTest extends BaseIntegrationTest {

    private final TagRepository repository;

    @Test
    void createShouldReturnCreatedEntity(){

        Tag tag = TagTestBuilder.defaultValues().build();

        assertThat(tag).isEqualTo(repository.save(tag));

    }

    @Test
    void findByIdShouldReturnEmptyOptionalIfIdIncorrect() {

        Optional<Tag> maybeCertificate = repository.findById(LAST_ENTITY_ID + 1);

        assertThat(maybeCertificate).isNotPresent();
    }


    @Test
    void findByIdShouldReturnInstanceOfTag(){

        assertThat(repository.findById(LAST_ENTITY_ID).get())
                .isInstanceOf(Tag.class);

    }


    @Test
    void findAllShouldReturnExpectedCountList(){

        Page<Tag> all = repository.findAll(Pageable.unpaged());

        assertThat(all.getContent()).hasSize(LAST_ENTITY_ID.intValue());

    }

    @Test
    void findAllShouldReturnEmptyList(){

        Page<Tag> all = repository.findAll(PageRequest.of(100, 5));

        assertThat(all.getContent())
                .isEmpty();

    }

    @Test
    void deleteShouldRemoveEntity(){

        repository.deleteById(LAST_ENTITY_ID);

        Optional<Tag> maybeCertificate = repository.findById(LAST_ENTITY_ID);

        assertThat(maybeCertificate).isNotPresent();

    }

    @Test
    void existsByIdShouldReturnTrueIfEntityProvided(){

        assertThat(repository.existsById(LAST_ENTITY_ID))
                .isTrue();

    }

    @Test
    void existsByIdShouldReturnFalseIfEntityNotProvided(){

        assertThat(repository.existsById(LAST_ENTITY_ID + 1))
                .isFalse();

    }

}