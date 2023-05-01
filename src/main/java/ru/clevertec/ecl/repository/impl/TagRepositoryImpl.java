package ru.clevertec.ecl.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Tag create(Tag entity) {
        Session session = sessionFactory.getCurrentSession();
        session.save(entity);
        return entity;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Tag.class, id));
    }

    @Override
    public List<Tag> findAll(Pageable pageable) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT tag FROM Tag tag", Tag.class)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Tag update(Tag updateDataEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(updateDataEntity);
        return updateDataEntity;
    }


    @Override
    public void delete(Tag entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Tag maybeTag = session.createQuery("SELECT tag FROM Tag tag WHERE tag.name = :name", Tag.class)
                .setParameter("name", name).uniqueResult();

        return Optional.ofNullable(maybeTag);
    }
}
