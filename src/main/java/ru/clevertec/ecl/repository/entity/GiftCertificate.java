package ru.clevertec.ecl.repository.entity;


import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate extends BaseEntity{
    private String name;
    private String description;
    private Double price;
    private Integer duration;

    @Builder
    public GiftCertificate(Long id, LocalDateTime createDate, LocalDateTime updateDate, String name, String description, Double price, Integer duration) {
        super(id, createDate, updateDate);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}
