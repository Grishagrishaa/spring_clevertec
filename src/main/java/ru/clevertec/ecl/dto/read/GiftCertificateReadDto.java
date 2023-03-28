package ru.clevertec.ecl.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GiftCertificateReadDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private List<TagReadDto> tags;
}
