package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.entity.FieldConfigSchema;
import org.example.pojo.vo.FieldConfigSchemaVO;

import java.util.List;
import java.util.Map;

/**
 * @author nanak
 */
public interface FieldConfigSchemaService extends IService<FieldConfigSchema> {
    Map<String, List<FieldConfigSchemaVO>> listGroupByComponentCode();
}
