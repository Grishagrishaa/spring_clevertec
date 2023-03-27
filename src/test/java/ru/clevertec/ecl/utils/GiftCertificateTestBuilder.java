package ru.clevertec.ecl.utils;

import lombok.Data;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;

import java.time.LocalDateTime;
import java.util.Collections;

import static ru.clevertec.ecl.utils.TestUtils.*;

@Data
public class GiftCertificateTestBuilder {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String name;
    private String description;
    private Double price;
    private Integer duration;

    public GiftCertificateTestBuilder() {
        this.id = 1L;
        this.createdDate = LocalDateTime.MIN;
        this.updatedDate = LocalDateTime.MIN;
        this.name = "Default";
        this.description = "Default";
        this.duration = 1000;
        this.price = 10.00;
    }


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
        return GiftCertificateCreateDto.builder()
                .name(gf.getName())
                .description(gf.getDescription())
                .price(gf.getPrice())
                .duration(gf.getDuration())
                .build();
    }

    public static GiftCertificateReadDto readDto(GiftCertificate gf){
        return GiftCertificateReadDto.builder()
                .id(gf.getId())
                .createDate(gf.getCreateDate())
                .updateDate(gf.getUpdateDate())
                .name(gf.getName())
                .description(gf.getDescription())
                .price(gf.getPrice())
                .duration(gf.getDuration())
                .build();
    }

    public GiftCertificate build(){
        return GiftCertificate.builder()
                .id(id)
                .createDate(createdDate)
                .updateDate(updatedDate)
                .name(name)
                .description(description)
                .price(price)
                .duration(duration)
                .build();

    }

}