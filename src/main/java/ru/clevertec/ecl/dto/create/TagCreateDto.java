package ru.clevertec.ecl.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateDto {
    @Size(min = 1, max = 50)
    @NotBlank(message = "Name is mandatory")
    private String name;
}
