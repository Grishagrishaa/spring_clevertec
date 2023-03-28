package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.service.mappers.api.ITagMapper;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    private final ITagMapper tagMapper;
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
        this.tagMapper = Mappers.getMapper(ITagMapper.class);
    }

    @Override
    @Transactional
    public TagReadDto create(@Valid TagCreateDto createDto) {
        return tagMapper.entityToReadDto(tagRepository.create(tagMapper.createDtoToEntity(createDto)));
    }

    @Override
    public TagReadDto findById(Long id) {
        return tagMapper.entityToReadDto(tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<TagReadDto> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable).stream()
                            .map(tagMapper::entityToReadDto)
                            .toList();
    }

    @Override
    @Transactional
    public TagReadDto update(@Valid TagCreateDto updateDataEntity, Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        tagMapper.update(tag, updateDataEntity);

        return tagMapper.entityToReadDto(tagRepository.update(tag));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        tagRepository.deleteById(id);
    }
}
