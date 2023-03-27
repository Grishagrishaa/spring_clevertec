package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGiftCertificateMapper {
    GiftCertificate createDtoToEntity(GiftCertificateCreateDto createDto);

    GiftCertificateReadDto entityToReadDto(GiftCertificate entity, List<Tag> tags);

    void update(@MappingTarget GiftCertificate entity, GiftCertificateCreateDto updateDto);

}
