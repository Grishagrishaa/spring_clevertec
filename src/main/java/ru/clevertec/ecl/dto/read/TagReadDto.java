package ru.clevertec.ecl.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TagReadDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String name;
}
