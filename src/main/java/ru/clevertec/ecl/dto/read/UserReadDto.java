package ru.clevertec.ecl.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;


@Data @Builder
@FieldNameConstants
@AllArgsConstructor @NoArgsConstructor
public class UserReadDto {

    private Long id;
    private Instant createDate;
    private Instant updateDate;
    private String mail;
    private String nick;

}
