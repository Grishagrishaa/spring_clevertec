package ru.clevertec.ecl.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.controller.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private final SessionFactory sessionFactory;

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAllByPageableAndCertificateFilter(Pageable pageable, GiftCertificateFilter filter) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);

        CriteriaQuery<GiftCertificate> queryByName = query.select(root)
                .where( !Objects.isNull( filter.getName()) ? cb.isTrue(cb.literal(true)) : cb.like(root.get("name"), "%" + filter.getName() + "%"),
                        !Objects.isNull( filter.getDescription()) ? cb.isTrue(cb.literal(true)) : cb.like(root.get("description"), "%" + filter.getDescription() + "%"),
                        !Objects.isNull( filter.getTagName()) ? cb.isTrue(cb.literal(true)) : cb.equal(root.join("tags").get("name"), filter.getTagName()));

        return session.createQuery(queryByName)
                      .setFirstResult(pageable.getPageNumber())
                      .setMaxResults(pageable.getPageSize())
                      .getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate updateDataEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(updateDataEntity);
        return updateDataEntity;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(giftCertificate);
    }
}
