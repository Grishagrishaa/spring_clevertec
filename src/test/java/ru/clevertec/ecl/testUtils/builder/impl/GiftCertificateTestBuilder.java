package ru.clevertec.ecl.testUtils.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.service.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.service.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.testUtils.builder.TestBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static ru.clevertec.ecl.testUtils.TestUtils.*;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "with")
public class GiftCertificateTestBuilder implements TestBuilder<GiftCertificate> {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String name;
    private String description;
    private Double price;
    private Integer duration;


    public static GiftCertificateTestBuilder randomValues(){
        GiftCertificateTestBuilder giftCertificateTestBuilder = new GiftCertificateTestBuilder();

        giftCertificateTestBuilder.setId(getRandomLong());
        giftCertificateTestBuilder.setCreatedDate(LocalDateTime.now());
        giftCertificateTestBuilder.setUpdatedDate(LocalDateTime.now());
        giftCertificateTestBuilder.setName(getRandomString());
        giftCertificateTestBuilder.setDescription(getRandomString());
        giftCertificateTestBuilder.setPrice(getRandomDouble());
        giftCertificateTestBuilder.setDuration(getRandomInt());

        return giftCertificateTestBuilder;
    }

    public static GiftCertificateCreateDto createDto(GiftCertificate gf){
        GiftCertificateCreateDto createDto = new GiftCertificateCreateDto();
        createDto.setName(gf.getName());
        createDto.setDescription(gf.getDescription());
        createDto.setPrice(gf.getPrice());
        createDto.setDuration(gf.getDuration());
        return createDto;
    }

    public static GiftCertificateReadDto readDto(GiftCertificate gf){
        GiftCertificateReadDto readDto = new GiftCertificateReadDto();

        readDto.setId(gf.getId());
        readDto.setCreateDate(gf.getCreateDate());
        readDto.setUpdateDate(gf.getUpdateDate());
        readDto.setName(gf.getName());
        readDto.setDescription(gf.getDescription());
        readDto.setPrice(gf.getPrice());
        readDto.setDuration(gf.getDuration());

        return readDto;
    }

    @Override
    public GiftCertificate build(){
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(id);
        certificate.setCreateDate(Instant.ofEpochSecond(createdDate.toEpochSecond(ZoneOffset.UTC)));
        certificate.setUpdateDate(Instant.ofEpochSecond(updatedDate.toEpochSecond(ZoneOffset.UTC)));
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        return certificate;
    }

}