package ru.clevertec.ecl.dto.read;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Data @Builder
@FieldNameConstants
@EqualsAndHashCode(exclude = {"id", "createDate", "updateDate"})
@AllArgsConstructor @NoArgsConstructor
public class GiftCertificateReadDto {
    private Long id;
    private Instant createDate;
    private Instant updateDate;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private List<TagReadDto> tags;
}
