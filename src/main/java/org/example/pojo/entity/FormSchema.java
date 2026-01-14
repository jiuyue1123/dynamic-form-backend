package org.example.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class FormSchema {
    @TableId(type = IdType.AUTO)
    private Long id;
    // 表单业务标识
    private String formId;
    private String formName;
    private String schemaVersion;
    private String formDesc;
    private String fields;
    private StatusEnum status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
