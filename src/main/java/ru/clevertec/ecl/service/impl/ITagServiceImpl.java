package ru.clevertec.ecl.service.impl;

import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.clevertec.ecl.controler.pagination.Pageable;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.api.IService;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.exceptions.OptimisticLockException;
import ru.clevertec.ecl.service.mappers.api.ITagMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@Transactional(readOnly = true)
public class ITagServiceImpl implements IService<TagCreateDto, TagReadDto> {
    private final ITagMapper tagMapper;
    private final ITagRepository tagRepository;

    public ITagServiceImpl(ITagRepository tagRepository) {
        this.tagRepository = tagRepository;
        this.tagMapper = Mappers.getMapper(ITagMapper.class);
    }

    @Override
    @Transactional
    public TagReadDto create(@Valid TagCreateDto createDto) {
        return tagMapper.entityToReadDto(tagRepository.create(tagMapper.createDtoToEntity(createDto)));
    }

    @Override
    public TagReadDto get(Long id) {
        return tagMapper.entityToReadDto(tagRepository.get(id));
    }

    @Override
    public List<TagReadDto> getAll(Pageable pageable) {
        return tagRepository.getAll(pageable).stream()
                            .map(tagMapper::entityToReadDto)
                            .toList();
    }

    @Override
    @Transactional
    public TagReadDto update(@Valid TagCreateDto updateDataEntity, Long id, LocalDateTime updateDate) {
        Tag tag = tagRepository.get(id);

        if(!tag.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException(tag + "WAS ALREADY UPDATED");
        }

        tagMapper.update(tag, updateDataEntity);

        return tagMapper.entityToReadDto(tagRepository.update(tag));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
