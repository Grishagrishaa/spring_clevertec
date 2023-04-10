package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Tag create(Tag entity);

    Optional<Tag> findById(Long id);

    List<Tag> findAll(Pageable pageable);

    Tag update(Tag updateDataEntity);

    void deleteById(Long id);

    boolean exists(Tag entity);
}
