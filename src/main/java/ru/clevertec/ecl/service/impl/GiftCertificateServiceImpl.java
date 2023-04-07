package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.BaseEntity;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.service.mappers.api.GiftCertificateMapper;
import ru.clevertec.ecl.service.mappers.api.TagMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final TagRepository tagRepository;
    private final GiftCertificateRepository giftRepository;

    private final GiftCertificateMapper certificateMapper;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public GiftCertificateReadDto create(@Valid GiftCertificateCreateDto createDto) {
        List<Long> tagIds = persistUnsavedTags(createDto);

        GiftCertificate giftCertificate = giftRepository.create(certificateMapper.createDtoToEntity(createDto));
        giftRepository.addTagsAssociation(giftCertificate.getId(), tagIds);

        return certificateMapper.entityToReadDto(giftCertificate, giftRepository.getAssociatedTags(giftCertificate.getId()));
    }

    @Override
    public GiftCertificateReadDto findById(Long id) {
        GiftCertificate certificate = giftRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Tag> associatedTags = giftRepository.getAssociatedTags(id);

        return certificateMapper.entityToReadDto(certificate, associatedTags);
    }

    @Override
    public List<GiftCertificateReadDto> findAllByGiftCertificateFilter(Pageable pageable, GiftCertificateFilter filter) {
         return giftRepository.findAllByPageableAndCertificateFilter(pageable, filter).stream()
                .map(gc -> certificateMapper.entityToReadDto(gc, giftRepository.getAssociatedTags(gc.getId())))
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateReadDto update(@Valid GiftCertificateCreateDto updateDataDto, Long id) {
        GiftCertificate giftCertificate = giftRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        giftRepository.addTagsAssociation(giftCertificate.getId(), persistUnsavedTags(updateDataDto));
        certificateMapper.update(giftCertificate, updateDataDto);

        return certificateMapper.entityToReadDto(
                giftRepository.update(giftCertificate),
                giftRepository.getAssociatedTags(giftCertificate.getId())
        );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        giftRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        giftRepository.deleteById(id);
    }


    private List<Long> persistUnsavedTags(GiftCertificateCreateDto createDto){
        return createDto.getTags().stream()
                .map(tagMapper::createDtoToEntity)
                .filter(tag -> !tagRepository.exists(tag))
                .map(tagRepository::create)
                .map(BaseEntity::getId)
                .toList();
    }


}
