package ru.clevertec.ecl.dto.read;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.ecl.controler.util.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TagReadDto {
    private Long id;
    private LocalDateTime createDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateDate;
    private String name;
}
