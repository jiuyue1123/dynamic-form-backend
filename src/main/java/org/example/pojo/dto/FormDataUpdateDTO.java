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
 * 表单数据更新DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDataUpdateDTO {
    @NotNull(message = "id is not null")
    private Long id;
    
    @NotEmpty(message = "formDataJson is not empty")
    private String formDataJson;
    
    private StatusEnum status;
    
    private String extInfo;
}
