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

    public ErrorMessage(String message) {
        this.logref = "error";
        this.message = message;
    }

    public ErrorMessage(String field, String message) {
        this.field = field;
        this.message = message;
    }
}