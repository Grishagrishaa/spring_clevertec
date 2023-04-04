package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.dto.create.TagCreateDto;
import ru.clevertec.ecl.service.dto.read.TagReadDto;

@Mapper
public interface TagMapper {
    Tag createDtoToEntity(TagCreateDto createDto);

    TagReadDto entityToReadDto(Tag entity);

    void update(@MappingTarget Tag entity, TagCreateDto updateDto);
}
