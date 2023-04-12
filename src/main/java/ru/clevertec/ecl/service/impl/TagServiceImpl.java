package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.service.mappers.api.TagMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public TagReadDto create(@Valid TagCreateDto createDto) {
        Tag entity = tagMapper.createDtoToEntity(createDto);
        return tagMapper.entityToReadDto(tagRepository.save(entity));
    }

    @Override
    public TagReadDto findById(Long id) {
        return tagMapper.entityToReadDto(tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<TagReadDto> findAll(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);

        return tagPage.map(tagMapper::entityToReadDto);
    }

    @Override
    public TagReadDto findMostPopularWithHighestCostByUserId(Long userId) {
        return tagMapper.entityToReadDto(tagRepository.findMostPopularWithHighestCostByUserId(userId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public TagReadDto update(@Valid TagCreateDto updateDataEntity, Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        tagMapper.update(tag, updateDataEntity);

        return tagMapper.entityToReadDto(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Tag> maybeTag = tagRepository.findById(id);
        maybeTag.ifPresentOrElse(tagRepository::delete, () -> { throw new EntityNotFoundException(); });
    }
}
