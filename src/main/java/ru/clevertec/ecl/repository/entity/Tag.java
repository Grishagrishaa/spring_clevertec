package ru.clevertec.ecl.repository.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends BaseEntity{
    private String name;

    @Builder
    public Tag(Long id, LocalDateTime createDate, LocalDateTime updateDate, String name) {
        super(id, createDate, updateDate);
        this.name = name;
    }
}
