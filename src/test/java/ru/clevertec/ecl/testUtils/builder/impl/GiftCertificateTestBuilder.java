package ru.clevertec.ecl.testUtils.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.testUtils.builder.TestBuilder;

import java.time.LocalDateTime;

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

    @Override
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