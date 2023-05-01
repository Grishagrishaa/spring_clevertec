package ru.clevertec.ecl.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data @Builder
@FieldNameConstants
@NoArgsConstructor @AllArgsConstructor
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
