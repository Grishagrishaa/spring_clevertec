package ru.clevertec.ecl.repository.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"createDate", "updateDate"})
@NoArgsConstructor
public class BaseEntity {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
