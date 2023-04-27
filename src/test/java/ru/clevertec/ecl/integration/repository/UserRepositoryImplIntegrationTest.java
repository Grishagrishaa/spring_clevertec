package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;
import ru.clevertec.ecl.testUtils.builder.impl.UserTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UserRepositoryImplIntegrationTest extends BaseIntegrationTest {

    private final UserRepository repository;

    private static final Long LAST_ENTITY_ID = 9L;

    @Test
    void createShouldReturnCreatedEntity(){

        User user = UserTestBuilder.defaultValues().build();

        assertThat(user).isEqualTo(repository.save(user));

    }

    @Test
    void findByIdShouldReturnEmptyOptionalIfIdIncorrect() {

        Optional<User> maybeUser = repository.findById(LAST_ENTITY_ID + 1);

        assertThat(maybeUser).isNotPresent();
    }


    @Test
    void findByIdShouldReturnInstanceOfTag(){

        assertThat(repository.findById(LAST_ENTITY_ID).get())
                .isInstanceOf(User.class);

    }


    @Test
    void findAllShouldReturnExpectedCountList(){

        Page<User> all = repository.findAll(Pageable.unpaged());

        assertThat(all.getContent()).hasSize(LAST_ENTITY_ID.intValue());

    }

    @Test
    void findAllShouldReturnEmptyList(){

        Page<User> all = repository.findAll(PageRequest.of(100, 5));

        assertThat(all.getContent())
                .isEmpty();

    }

    @Test
    void deleteShouldRemoveEntity(){

        repository.deleteById(LAST_ENTITY_ID);

        Optional<User> maybeUser = repository.findById(LAST_ENTITY_ID);

        assertThat(maybeUser).isNotPresent();

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