package org.example.pojo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.StatusEnum;

/**
 * @author nanak
 *
 * 表单数据保存DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDataSaveDTO {
    @NotEmpty(message = "formId is not empty")
    private String formId;
    
    @NotEmpty(message = "schemaVersion is not empty")
    private String schemaVersion;
    
    @NotEmpty(message = "formDataJson is not empty")
    private String formDataJson;
    
    @NotNull(message = "status is not null")
    private StatusEnum status;
    
    private String extInfo;
}
