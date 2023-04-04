package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.service.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.service.dto.read.GiftCertificateReadDto;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateReadDto create(GiftCertificateCreateDto createDto);

    GiftCertificateReadDto findById(Long id);

    List<GiftCertificateReadDto> findAllByGiftCertificateFilter(Pageable pageable, GiftCertificateFilter filter);

    GiftCertificateReadDto update(GiftCertificateCreateDto updateDataEntity, Long id);

    void deleteById(Long id);
}
