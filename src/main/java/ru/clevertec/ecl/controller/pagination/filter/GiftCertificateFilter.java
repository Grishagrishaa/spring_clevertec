package ru.clevertec.ecl.controller.pagination.filter;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class GiftCertificateFilter {
    @Size(min = 1, max = 20)
    private String tagName;
    @Size(min = 1, max = 30)
    private String name;
    @Size(min = 1, max = 80)
    private String description;

    public static GiftCertificateFilter defaultValues(){
        return GiftCertificateFilter.builder().build();
    }
}
