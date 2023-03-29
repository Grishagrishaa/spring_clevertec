package ru.clevertec.ecl.repository.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
