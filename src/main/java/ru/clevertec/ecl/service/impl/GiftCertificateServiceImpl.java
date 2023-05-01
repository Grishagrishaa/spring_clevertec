package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.controller.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.service.dto.create.TagCreateDto;
import ru.clevertec.ecl.service.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.service.mappers.api.GiftCertificateMapper;
import ru.clevertec.ecl.service.mappers.api.TagMapper;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateMapper certificateMapper = Mappers.getMapper(GiftCertificateMapper.class);
    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    private final GiftCertificateRepository giftRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public GiftCertificateReadDto create(@Valid GiftCertificateCreateDto createDto) {
        GiftCertificate entity = certificateMapper.createDtoToEntity(createDto);
        addPersistentTagsToEntity(entity, createDto.getTags());
        return certificateMapper.entityToReadDto(giftRepository.create(entity));
    }

    @Override
    public GiftCertificateReadDto findById(Long id) {
        GiftCertificate certificate = giftRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return certificateMapper.entityToReadDto(certificate);
    }

    @Override
    public List<GiftCertificateReadDto> findAllByGiftCertificateFilter(Pageable pageable, GiftCertificateFilter filter) {
         return giftRepository.findAllByPageableAndCertificateFilter(pageable, filter).stream()
                .map(certificateMapper::entityToReadDto)
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateReadDto update(@Valid GiftCertificateCreateDto updateDataDto, Long id) {
        GiftCertificate giftCertificate = giftRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        certificateMapper.update(giftCertificate, updateDataDto);

        giftCertificate.getTags().clear();
        addPersistentTagsToEntity(giftCertificate, updateDataDto.getTags());

        return certificateMapper.entityToReadDto(giftRepository.update(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<GiftCertificate> maybeCertificate = giftRepository.findById(id);
        maybeCertificate.ifPresentOrElse(giftRepository::delete, () -> { throw new EntityNotFoundException(); });
    }

    @Transactional
    void addPersistentTagsToEntity(GiftCertificate entity, List<TagCreateDto> tagCreateDtos) {
        tagCreateDtos.stream()
                     .map(tagMapper::createDtoToEntity)
                     .map(tag -> tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.create(tag)))
                     .forEach(entity::addTag);
    }
}
