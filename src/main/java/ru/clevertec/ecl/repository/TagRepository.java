package ru.clevertec.ecl.repository;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    Tag create(Tag entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<Tag> findById(Long id);

    /**
     * Retrieves an entity by its name.
     *
     * @param name must not be {@literal null}.
     * @return the entity with the given name or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal name} is {@literal null}.
     */
    Optional<Tag> findByName(String name);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<Tag> findAll(Pageable pageable);

    /**
     * Updates a given entity.
     *
     * @param updateDataEntity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    Tag update(Tag updateDataEntity);

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    void delete(Tag entity);

}
