package ru.clevertec.ecl.dto.errors;

import lombok.Builder;
import lombok.Getter;
import ru.clevertec.ecl.dto.errors.ErrorMessage;

import java.util.Set;

@Getter
@Builder
public class StructuredError {
    private final String logref;
    private final Set<ErrorMessage> errors;
}