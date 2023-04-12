package ru.clevertec.ecl.dto.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private String logref;
    private String field;
    private String message;
}