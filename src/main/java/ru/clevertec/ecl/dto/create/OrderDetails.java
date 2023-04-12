package ru.clevertec.ecl.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class OrderDetails {

    @NotNull
    @Positive
    private Long userId;
    @NotNull
    @Positive
    private Long giftCertificateId;

}
