package ru.clevertec.ecl.service.impl;

import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.clevertec.ecl.controler.pagination.Pageable;
import ru.clevertec.ecl.repository.api.IGiftCertificateRepository;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.entity.BaseEntity;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.service.api.IService;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.exceptions.IncorrectParameterException;
import ru.clevertec.ecl.exceptions.OptimisticLockException;
import ru.clevertec.ecl.service.mappers.api.IGiftCertificateMapper;
import ru.clevertec.ecl.service.mappers.api.ITagMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@Transactional(readOnly = true)
public class IGiftCertificateServiceImpl implements IService<GiftCertificateCreateDto, GiftCertificateReadDto> {
    private final IGiftCertificateMapper certificateMapper = Mappers.getMapper(IGiftCertificateMapper.class);
    private final ITagMapper tagMapper = Mappers.getMapper(ITagMapper.class);

    private final ITagRepository tagRepository;
    private final IGiftCertificateRepository giftRepository;

    public IGiftCertificateServiceImpl(ITagRepository tagRepository, IGiftCertificateRepository giftRepository) {
        this.tagRepository = tagRepository;
        this.giftRepository = giftRepository;
    }

    @Override
    @Transactional
    public GiftCertificateReadDto create(@Valid GiftCertificateCreateDto createDto) {
        List<Long> tagIds = persistUnsavedTags(createDto);

        GiftCertificate giftCertificate = giftRepository.create(certificateMapper.createDtoToEntity(createDto));
        giftRepository.addTagsAssociation(giftCertificate.getId(), tagIds);

        return certificateMapper.entityToReadDto(giftCertificate, giftRepository.getAssociatedTags(giftCertificate.getId()));
    }

    @Override
    public GiftCertificateReadDto get(Long id) throws IncorrectParameterException {
        return certificateMapper.entityToReadDto(giftRepository.get(id), giftRepository.getAssociatedTags(id));
    }

    @Override
    public List<GiftCertificateReadDto> getAll(Pageable pageable) {
         return giftRepository.getAll(pageable).stream()
                .map(gc -> certificateMapper.entityToReadDto(gc, giftRepository.getAssociatedTags(gc.getId())))
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateReadDto update(@Valid GiftCertificateCreateDto updateDataDto, Long id, LocalDateTime updateDate) {
        GiftCertificate giftCertificate = giftRepository.get(id);

        if(!giftCertificate.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException(giftCertificate + "WAS ALREADY UPDATED");
        }

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
        giftRepository.deleteById(id);
    }


    @Transactional
    List<Long> persistUnsavedTags(GiftCertificateCreateDto createDto){
        return createDto.getTags().stream()
                .map(tagMapper::createDtoToEntity)
                .filter(tag -> !tagRepository.exists(tag))
                .map(tagRepository::create)
                .map(BaseEntity::getId)
                .toList();
    }


}
