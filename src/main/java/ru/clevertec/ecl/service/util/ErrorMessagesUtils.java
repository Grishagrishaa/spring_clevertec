package ru.clevertec.ecl.service.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class ErrorMessagesUtils {
    @Value("${app.tag.errorMessages.TAG_NOT_FOUND}")
    public static String TAG_NOT_FOUND;

}
