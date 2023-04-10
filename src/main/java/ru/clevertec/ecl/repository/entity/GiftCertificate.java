package ru.clevertec.ecl.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, duration);
    }
}
