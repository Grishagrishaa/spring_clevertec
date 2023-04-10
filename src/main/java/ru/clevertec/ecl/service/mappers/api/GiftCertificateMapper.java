package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface GiftCertificateMapper {
    @Mapping(target = "createDate", expression ="java( LocalDateTime.now() )")
    @Mapping(target = "updateDate", expression ="java( LocalDateTime.now() )")
    GiftCertificate createDtoToEntity(GiftCertificateCreateDto createDto);

    GiftCertificateReadDto entityToReadDto(GiftCertificate entity, List<Tag> tags);

    @Mapping(target = "updateDate", expression ="java( LocalDateTime.now() )")
    void update(@MappingTarget GiftCertificate entity, GiftCertificateCreateDto updateDto);
}
