package ru.clevertec.ecl.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"createDate", "updateDate"})
public class TagReadDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String name;
}
