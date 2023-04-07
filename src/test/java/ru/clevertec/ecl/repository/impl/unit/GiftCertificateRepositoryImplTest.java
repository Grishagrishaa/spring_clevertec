package ru.clevertec.ecl.repository.impl.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.impl.GiftCertificateRepositoryImpl;
import ru.clevertec.ecl.service.util.GCRequestUtils;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateRepositoryImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private GiftCertificateRepositoryImpl repository;

    @Captor
    ArgumentCaptor<BeanPropertyRowMapper<GiftCertificate>> rowMapperCaptor;

    @Test
    void createShouldReturnCreatedEntity(){
        GiftCertificate entity = GiftCertificateTestBuilder.defaultValues().build();
        //Cause i use "new" in implementation -> can't mock it
        doReturn(entity).when(jdbcTemplate).queryForObject(eq(GCRequestUtils.CREATE_GC_SQL), rowMapperCaptor.capture(),
                                                            eq(entity.getCreateDate()), eq(entity.getUpdateDate()),
                                                            eq(entity.getName()), eq(entity.getDescription()),
                                                            eq(entity.getPrice()), eq(entity.getDuration()));
        assertThat(entity).isEqualTo(repository.create(entity));
    }

    @Test
    void findByIdShouldReturnEntity() {
        GiftCertificate giftCertificate = GiftCertificateTestBuilder.defaultValues().build();
        doReturn(giftCertificate).when(jdbcTemplate).queryForObject(eq(GCRequestUtils.GET_GC_BY_ID_SQL), rowMapperCaptor.capture(), eq(giftCertificate.getId()));

        assertThat(giftCertificate).isEqualTo(repository.findById(giftCertificate.getId()).get());

    }

    @Test
    void findByIdShouldThrowExceptionIfIdIncorrect() {
        GiftCertificate giftCertificate = GiftCertificateTestBuilder.defaultValues().build();
        Optional<Tag> tagOptional = Optional.empty();
        doReturn(null).when(jdbcTemplate).queryForObject(eq(GCRequestUtils.GET_GC_BY_ID_SQL), rowMapperCaptor.capture(), eq(giftCertificate.getId()));


        assertThat(repository.findById(giftCertificate.getId())).isNotPresent();

    }

    @Test
    void findAllByPageableAndCertificateFilterShouldReturnExpectedList() {
        Pageable pageable = PageRequest.of(0, 1);

        List<GiftCertificate> expected = List.of(GiftCertificateTestBuilder.defaultValues().build());
        doReturn(expected)
                .when(jdbcTemplate).query(eq(GCRequestUtils.getFilteredRequest(GiftCertificateFilter.defaultValues())), rowMapperCaptor.capture(), eq(pageable.getPageSize()), eq(pageable.getOffset()));


        assertThat(repository.findAllByPageableAndCertificateFilter(pageable, GiftCertificateFilter.defaultValues())).isEqualTo(expected);
    }

    @Test
    void updateShouldReturnEntityAndCallRepository() {
        GiftCertificate giftCertificate = GiftCertificateTestBuilder.defaultValues().build();
        doReturn(giftCertificate).when(jdbcTemplate).queryForObject(eq(GCRequestUtils.UPDATE_GC_SQL), rowMapperCaptor.capture(),
                                                                        eq(giftCertificate.getCreateDate()), eq(giftCertificate.getUpdateDate()),
                                                                        eq(giftCertificate.getName()), eq(giftCertificate.getDescription()),
                                                                        eq(giftCertificate.getPrice()), eq(giftCertificate.getDuration()), eq(giftCertificate.getId()));

        assertThat(giftCertificate).isEqualTo(repository.update(giftCertificate));
        verify(jdbcTemplate).queryForObject(eq(GCRequestUtils.UPDATE_GC_SQL), rowMapperCaptor.capture(),
                                                                        eq(giftCertificate.getCreateDate()), eq(giftCertificate.getUpdateDate()),
                                                                        eq(giftCertificate.getName()), eq(giftCertificate.getDescription()),
                                                                        eq(giftCertificate.getPrice()), eq(giftCertificate.getDuration()), eq(giftCertificate.getId()));
    }

    @Test
    void deleteByIdShouldCallJdbcTemplate() {
        repository.deleteById(1L);
        verify(jdbcTemplate).update(GCRequestUtils.DELETE_GC_BY_ID, 1L);
    }
}