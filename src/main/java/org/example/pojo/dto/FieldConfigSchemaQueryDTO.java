package org.example.pojo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author nanak
 */
@Data
public class FieldConfigSchemaQueryDTO {
    @NotEmpty
    private String componentCode;
}
