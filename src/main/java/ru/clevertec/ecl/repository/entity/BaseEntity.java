package ru.clevertec.ecl.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
