package ru.clevertec.ecl.service.dto.create;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GiftCertificateCreateDto {
    @Size(min = 1, max = 50)
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Size(min = 10, max = 200)
    @NotBlank(message = "Description is mandatory")
    private String description;
    @Min(1)
    private Double price;
    @Positive
    private Integer duration;
    @NotEmpty
    private List<TagCreateDto> tags;
}
