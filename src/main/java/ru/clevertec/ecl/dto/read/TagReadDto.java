package ru.clevertec.ecl.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;
import java.util.Objects;

@Data @Builder
@FieldNameConstants
@EqualsAndHashCode(exclude = {"id", "createDate", "updateDate"})
@AllArgsConstructor @NoArgsConstructor
public class TagReadDto {
    private Long id;
    private Instant createDate;
    private Instant updateDate;
    private String name;
}
