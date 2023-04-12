package ru.clevertec.ecl.dto.read;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data @Builder
@EqualsAndHashCode
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
