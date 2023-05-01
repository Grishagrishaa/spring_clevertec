package ru.clevertec.ecl.service.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
