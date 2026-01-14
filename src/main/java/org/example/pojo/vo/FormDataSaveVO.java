package org.example.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.StatusEnum;

import java.time.LocalDateTime;

/**
 * @author nanak
 *
 * 表单数据实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDataSaveVO {
    private Long id;
    private String formId;
    private String schemaVersion;
    private String formDataJson;
    private LocalDateTime submitTime;
    private StatusEnum status;
    private String extInfo;
}
