package ru.clevertec.ecl.service.mappers.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    default GiftCertificate createDtoToEntity(GiftCertificateCreateDto createDto){
        return GiftCertificate.builder()
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .name(createDto.getName())
                .description(createDto.getDescription())
                .price(createDto.getPrice())
                .duration(createDto.getDuration())
                .build();
    }

    GiftCertificateReadDto entityToReadDto(GiftCertificate entity, List<Tag> tags);

    default void update(@MappingTarget GiftCertificate entity, GiftCertificateCreateDto updateDto){
        entity.setUpdateDate(LocalDateTime.now());
        entity.setName(updateDto.getName());
        entity.setDescription(updateDto.getDescription());
        entity.setPrice(updateDto.getPrice());
        entity.setDuration(updateDto.getDuration());
    }

}
