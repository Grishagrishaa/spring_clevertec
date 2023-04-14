package ru.clevertec.ecl.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;

public interface GiftCertificateService {

    GiftCertificateReadDto create(GiftCertificateCreateDto createDto);

    GiftCertificateReadDto findById(Long id);

    Page<GiftCertificateReadDto> findAllByGiftCertificateFilter(Pageable pageable, GiftCertificateFilter filter);

    GiftCertificateReadDto update(GiftCertificateCreateDto updateDataEntity, Long id);

    void deleteById(Long id);

}
