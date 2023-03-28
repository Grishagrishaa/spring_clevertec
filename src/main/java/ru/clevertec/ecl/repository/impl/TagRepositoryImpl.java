package ru.clevertec.ecl.repository.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.util.TagRequestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(Tag entity) {
        return jdbcTemplate.queryForObject(TagRequestUtils.CREATE_TAG_SQL, new BeanPropertyRowMapper<>(Tag.class),
                                           LocalDateTime.now(), LocalDateTime.now(),
                                           entity.getName());
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(TagRequestUtils.GET_TAG_BY_ID_SQL,
                                                                   new BeanPropertyRowMapper<>(Tag.class), id));
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll(Pageable pageable) {
        return jdbcTemplate.query(TagRequestUtils.GET_ALL_TAGS_WITH_LIMIT_OFFSET_SQL,
                                  new BeanPropertyRowMapper<>(Tag.class),
                                  pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public Tag update(Tag updateDataEntity) {
        return jdbcTemplate.queryForObject(TagRequestUtils.UPDATE_TAG_SQL, new BeanPropertyRowMapper<>(Tag.class),
                                           updateDataEntity.getCreateDate(), LocalDateTime.now(),
                                           updateDataEntity.getName(), updateDataEntity.getId());
    }


    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(TagRequestUtils.DELETE_TAG_BY_ID, id);
    }

    @Override
    public boolean exists(Tag entity) {
        return jdbcTemplate.query(TagRequestUtils.IS_EXISTS_TAG_BY_NAME,
                                   new BeanPropertyRowMapper<>(Tag.class),
                                   entity.getName())
                           .size() != 0;
    }
}
