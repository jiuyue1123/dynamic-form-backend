package org.example.config;

import org.example.enums.StatusEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author nanak
 * 
 * Converter for converting String to StatusEnum by integer value
 */
@Component
public class StringToStatusEnumConverter implements Converter<String, StatusEnum> {
    
    @Override
    public StatusEnum convert(String source) {
        try {
            int value = Integer.parseInt(source);
            for (StatusEnum status : StatusEnum.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid StatusEnum value: " + source);
        } catch (NumberFormatException e) {
            // Try to match by name as fallback
            return StatusEnum.valueOf(source.toUpperCase());
        }
    }
}
