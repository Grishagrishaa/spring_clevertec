package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.dto.create.UserCreateDto;
import ru.clevertec.ecl.dto.read.UserReadDto;
import ru.clevertec.ecl.repository.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User createDtoToEntity(UserCreateDto createDto);

    UserReadDto entityToReadDto(User entity);

    void update(@MappingTarget User entity, UserCreateDto updateDto);

}
