package ru.clevertec.ecl.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserCreateDto{

    @Email
    private String mail;
    @NotEmpty
    @Size(min = 2, max = 30)
    private String nick;

}
