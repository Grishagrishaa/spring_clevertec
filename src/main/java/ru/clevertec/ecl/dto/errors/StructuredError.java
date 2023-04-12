package ru.clevertec.ecl.dto.errors;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class StructuredError {
    private final String logref;
    private final Set<ErrorMessage> errors;
}