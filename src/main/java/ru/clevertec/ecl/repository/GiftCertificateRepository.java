package ru.clevertec.ecl.repository;


import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    GiftCertificate create(GiftCertificate entity);

    Optional<GiftCertificate> findById(Long id);

    List<GiftCertificate> findAllByPageableAndCertificateFilter(Pageable pageable, GiftCertificateFilter filter);

    GiftCertificate update(GiftCertificate updateDataEntity);

    void delete(GiftCertificate giftCertificate);


}
