package ru.clevertec.ecl.controler.pagination.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class GiftCertificateIFilter {
    private String tagName;
    private String name;
    private String description;

    public static GiftCertificateIFilter defaultValues(){
        return new GiftCertificateIFilter(null, null, null);
    }
}
