package ru.clevertec.ecl.repository.impl.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import ru.clevertec.ecl.service.util.TagRequestUtils;
import ru.clevertec.ecl.testUtils.builder.impl.GiftCertificateTestBuilder;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateRepositoryImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private GiftCertificateRepositoryImpl repository;

    @Test
    void createShouldReturnCreatedEntity(){
        GiftCertificate giftCertificate = GiftCertificateTestBuilder.randomValues().build();
        //Cause i use "new" in implementation -> can't mock it
        doReturn(giftCertificate).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class),
                                                        any(), any(), any(),
                                                        anyString(), anyDouble(), anyInt());
        assertThat(giftCertificate).isEqualTo(repository.create(giftCertificate));
    }

    @Test
    void findByIdShouldReturnEntity() {
        GiftCertificate giftCertificate = GiftCertificateTestBuilder.randomValues().build();
        doReturn(giftCertificate).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyLong());

        assertThat(giftCertificate).isEqualTo(repository.findById(giftCertificate.getId()).get());

    }

    @Test
    void findByIdShouldThrowExceptionIfIdIncorrect() {
        GiftCertificate giftCertificate = GiftCertificateTestBuilder.randomValues().build();
        Optional<Tag> tagOptional = Optional.empty();
        doReturn(null).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyLong());

        ;
        assertThat(repository.findById(giftCertificate.getId())).isNotPresent();

    }

    @Test
    void findAllByPageableAndCertificateFilterShouldReturnExpectedList() {
        Pageable pageable = PageRequest.of(0, 1);

        List<GiftCertificate> expected = List.of(GiftCertificateTestBuilder.randomValues().build());
        doReturn(expected)
                .when(jdbcTemplate).query(anyString(), any(BeanPropertyRowMapper.class), anyInt(), anyLong());


        assertThat(repository.findAllByPageableAndCertificateFilter(pageable, GiftCertificateFilter.defaultValues())).isEqualTo(expected);
    }

    @Test
    void updateShouldReturnEntityAndCallRepository() {
        GiftCertificate giftCertificate = GiftCertificateTestBuilder.randomValues().build();
        doReturn(giftCertificate).when(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class),
                                                                    any(), any(),
                                                                    anyString(), anyString(),
                                                                    anyDouble(), anyInt(), anyLong());

        assertThat(giftCertificate).isEqualTo(repository.update(giftCertificate));
        verify(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class),
                                            any(), any(),
                                            anyString(), anyString(),
                                            anyDouble(), anyInt(), anyLong());
    }

    @Test
    void deleteByIdShouldCallJdbcTemplate() {
        repository.deleteById(1L);
        verify(jdbcTemplate).update(GCRequestUtils.DELETE_GC_BY_ID, 1L);
    }
}