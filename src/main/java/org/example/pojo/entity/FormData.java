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
 * 表单数据实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormData {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String formId;
    private String schemaVersion;
    private String formDataJson;
    private LocalDateTime submitTime;
    private StatusEnum status;
    private String extInfo;
}
