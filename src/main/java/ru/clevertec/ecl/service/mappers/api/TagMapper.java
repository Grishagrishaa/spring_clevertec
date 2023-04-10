package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface TagMapper {
    @Mapping(target = "createDate", expression ="java( LocalDateTime.now() )")
    @Mapping(target = "updateDate", expression ="java( LocalDateTime.now() )")
    Tag createDtoToEntity(TagCreateDto createDto);

    TagReadDto entityToReadDto(Tag entity);

    @Mapping(target = "updateDate", expression ="java( LocalDateTime.now() )")
    void update(@MappingTarget Tag entity, TagCreateDto updateDto);
}
