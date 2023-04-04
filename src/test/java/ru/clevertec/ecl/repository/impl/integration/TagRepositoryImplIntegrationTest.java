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
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.impl.TagRepositoryImpl;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@Rollback
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateTestConf.class)
class TagRepositoryImplIntegrationTest {
    @Autowired
    private SessionFactory testSessionFactory;

    private final TagRepository repository = new TagRepositoryImpl(testSessionFactory);

    private static final Long COUNT_OF_TAGS = 4L;


    @Test
    void createShouldReturnCreatedEntity(){
        Tag tag = TagTestBuilder.randomValues().build();
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
        repository.delete(getTagFromDml());
        assertThat(repository.findAll(PageRequest.of(0, 5))).hasSize( COUNT_OF_TAGS.intValue() - 1);
    }

    @Test
    void existsShouldReturnTrueIfEntityProvided(){
        assertThat(repository.findByName(getTagFromDml().getName())).isPresent();
    }

    @Test
    void existsShouldReturnFalseIfEntityNotProvided(){
        assertThat(repository.findByName(TagTestBuilder.randomValues().build().getName())).isNotPresent();
    }

    private Tag getTagFromDml(){
        Tag tag = new Tag("tag_name1");
        tag.setId(1L);
        return tag;
    }
}