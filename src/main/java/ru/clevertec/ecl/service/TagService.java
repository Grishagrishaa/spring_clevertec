package ru.clevertec.ecl.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;

public interface TagService {

    TagReadDto create(TagCreateDto createDto);

    TagReadDto findById(Long id);

    Page<TagReadDto> findAll(Pageable pageable);

    TagReadDto update(TagCreateDto updateDataEntity, Long id);

    void deleteById(Long id);

    TagReadDto findMostPopularWithHighestCostByUserId(Long userId);

}
