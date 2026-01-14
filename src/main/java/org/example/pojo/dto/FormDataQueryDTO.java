package org.example.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.StatusEnum;

import java.time.LocalDateTime;

/**
 * @author nanak
 *
 * 表单数据查询DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDataQueryDTO {
    private String formId;
    private String schemaVersion;
    private StatusEnum status;
    private LocalDateTime submitTimeStart;
    private LocalDateTime submitTimeEnd;
    private Integer pageNum;
    private Integer pageSize;
}
