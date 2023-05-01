package ru.clevertec.ecl.repository;


import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.controller.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository{

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
    GiftCertificate create(GiftCertificate entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<GiftCertificate> findById(Long id);

    /**
     * Return List of entities.
     *
     * @param pageable must not be {@literal null}. Define limit and offset - pagination.
     * @param filter must not be {@literal null}. Contains params for request. (name and etc.)
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    List<GiftCertificate> findAllByPageableAndCertificateFilter(Pageable pageable, GiftCertificateFilter filter);

    /**
     * Updates a given entity.
     *
     * @param updateDataEntity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    GiftCertificate update(GiftCertificate updateDataEntity);

    /**
     * Deletes a given entity.
     *
     * @param giftCertificate must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    void delete(GiftCertificate giftCertificate);


}
