package ru.clevertec.ecl.repository.impl;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.controler.pagination.Pageable;
import ru.clevertec.ecl.repository.api.IGiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.exceptions.IncorrectParameterException;
import ru.clevertec.ecl.service.util.GCRequestUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class IGiftCertificateRepositoryImpl implements IGiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;

    public IGiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) throws IncorrectParameterException {
        entity.setUpdateDate(LocalDateTime.now());
        entity.setCreateDate(LocalDateTime.now());
        return jdbcTemplate.queryForObject(GCRequestUtils.CREATE_GC_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class),
                                             entity.getCreateDate(), entity.getUpdateDate(),
                                             entity.getName(), entity.getDescription(),
                                             entity.getPrice(), entity.getDuration());
    }

    @Override
    public GiftCertificate get(Long id) throws IncorrectParameterException {
        return jdbcTemplate.queryForObject(GCRequestUtils.GET_GC_BY_ID_SQL,
                                   new BeanPropertyRowMapper<>(GiftCertificate.class), id);
    }

    @Override
    public List<GiftCertificate> getAll(Pageable pageable) {
        return jdbcTemplate.query(GCRequestUtils.getFilteredRequest(pageable.getFilter()),
                                  new BeanPropertyRowMapper<>(GiftCertificate.class),
                                  pageable.getLimit(), pageable.getOffset());
//        return jdbcTemplate.query(GCRequestUtils.GET_ALL_GC_WITH_LIMIT_OFFSET_SQL,
//                                  new BeanPropertyRowMapper<>(GiftCertificate.class),
//                                  pageable.getLimit(), pageable.getOffset());
    }

    @Override
    public GiftCertificate update(GiftCertificate updateDataEntity) {
        return jdbcTemplate.queryForObject(GCRequestUtils.UPDATE_GC_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class),
                                           updateDataEntity.getCreateDate(), LocalDateTime.now(),
                                           updateDataEntity.getName(), updateDataEntity.getDescription(),
                                           updateDataEntity.getPrice(), updateDataEntity.getDuration(),
                                           updateDataEntity.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(GCRequestUtils.DELETE_GC_BY_ID, id);
    }

    @Override
    public void addTagsAssociation(long certificateId, List<Long> tagIds) {
        jdbcTemplate.batchUpdate(GCRequestUtils.ADD_TAGS_ASSOCIATION_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, certificateId);
                ps.setLong(2, tagIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return tagIds.size();
            }
        });
    }


    @Override
    public List<Tag> getAssociatedTags(long certificateId) {
        return jdbcTemplate.query(GCRequestUtils.GET_ASSOCIATED_TAGS_BY_GC_ID, new BeanPropertyRowMapper<>(Tag.class), certificateId);
    }
}
