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
 * 表单元元数据实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormSchemaSaveVO {
    private Long id;
    // 表单业务标识
    private String formId;
    private String formName;
    private String schemaVersion;
    private String formDesc;
    private StatusEnum status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
