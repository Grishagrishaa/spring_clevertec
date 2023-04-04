package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.service.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.service.dto.read.GiftCertificateReadDto;

@Mapper(uses = TagMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GiftCertificateMapper {
    @Mapping(target = "tags", ignore = true)
    GiftCertificate createDtoToEntity(GiftCertificateCreateDto createDto);

    GiftCertificateReadDto entityToReadDto(GiftCertificate entity);

    @Mapping(target = "tags", ignore = true)
    void update(@MappingTarget GiftCertificate entity, GiftCertificateCreateDto updateDto);

}
