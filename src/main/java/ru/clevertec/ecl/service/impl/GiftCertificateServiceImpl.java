package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.service.mappers.api.GiftCertificateMapper;
import ru.clevertec.ecl.service.mappers.api.TagMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.repository.util.CertificateSpec.like;
import static ru.clevertec.ecl.repository.util.CertificateSpec.tagNameIn;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateMapper certificateMapper;
    private final TagMapper tagMapper;

    private final GiftCertificateRepository giftRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public GiftCertificateReadDto create(@Valid GiftCertificateCreateDto createDto) {
        GiftCertificate entity = certificateMapper.createDtoToEntity(createDto);
        addPersistentTagsToEntity(entity, createDto.getTags());
        return certificateMapper.entityToReadDto(giftRepository.save(entity));
    }

    @Override
    public GiftCertificateReadDto findById(Long id) {
        GiftCertificate certificate = giftRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return certificateMapper.entityToReadDto(certificate);
    }

    @Override
    public Page<GiftCertificateReadDto> findAllByGiftCertificateFilter(Pageable pageable, GiftCertificateFilter filter) {
        Specification<GiftCertificate> spec = Specification.where(like("name", filter.getName())
                                                           .and(like("description", filter.getDescription())
                                                           .and(tagNameIn(filter.getTagName()))));
        Page<GiftCertificate> certificatePage = giftRepository.findAll(spec, pageable);

        return certificatePage.map(certificateMapper::entityToReadDto);
    }

    @Override
    @Transactional
    public GiftCertificateReadDto update(@Valid GiftCertificateCreateDto updateDataDto, Long id) {
        GiftCertificate giftCertificate = giftRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        certificateMapper.update(giftCertificate, updateDataDto);

        giftCertificate.getTags().clear();
        addPersistentTagsToEntity(giftCertificate, updateDataDto.getTags());

        return certificateMapper.entityToReadDto(giftRepository.save(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<GiftCertificate> maybeCertificate = giftRepository.findById(id);
        maybeCertificate.ifPresentOrElse(giftRepository::delete, () -> { throw new EntityNotFoundException(); });
    }

    private void addPersistentTagsToEntity(GiftCertificate entity, List<TagCreateDto> tagCreateDtos) {
        tagCreateDtos.stream()
                     .map(tagMapper::createDtoToEntity)
                     .map(tag -> tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.save(tag)))
                     .forEach(entity::addTag);
    }

}
