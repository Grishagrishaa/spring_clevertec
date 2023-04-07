package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface TagMapper {
    default Tag createDtoToEntity(TagCreateDto createDto){
        return Tag.builder()
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .name(createDto.getName())
                .build();
    }

    TagReadDto entityToReadDto(Tag entity);

    default void update(@MappingTarget Tag entity, TagCreateDto updateDto){
        entity.setUpdateDate(LocalDateTime.now());
        entity.setName(updateDto.getName());
    }
}
