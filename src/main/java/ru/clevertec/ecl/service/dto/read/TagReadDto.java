package ru.clevertec.ecl.service.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class TagReadDto {
    private Long id;
    private Instant createDate;
    private Instant updateDate;
    private String name;
}
