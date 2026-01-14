package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.entity.FieldComponent;
import org.example.pojo.vo.FieldComponentVO;

import java.util.List;
import java.util.Map;

/**
 * @author nanak
 */
public interface FieldComponentService extends IService<FieldComponent> {
    Map<String, List<FieldComponentVO>> listGroupByType();
}
