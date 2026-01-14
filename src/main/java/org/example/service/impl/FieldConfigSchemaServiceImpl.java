package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.FieldConfigSchemaMapper;
import org.example.pojo.entity.FieldConfigSchema;
import org.example.pojo.vo.FieldConfigSchemaVO;
import org.example.service.FieldConfigSchemaService;
import org.example.utils.ToolKit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nanak
 */
@Service
public class FieldConfigSchemaServiceImpl extends ServiceImpl<FieldConfigSchemaMapper, FieldConfigSchema> implements FieldConfigSchemaService {
    @Override
    public Map<String, List<FieldConfigSchemaVO>> listGroupByComponentCode() {
        Map<String, List<FieldConfigSchemaVO>> map = new LinkedHashMap<>();

        QueryWrapper<FieldConfigSchema> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        for (FieldConfigSchema fieldConfigSchema : this.list()) {
            // 判断component_type字段
            if (!map.containsKey(fieldConfigSchema.getComponentCode())) {
                map.put(fieldConfigSchema.getComponentCode(), new ArrayList<>());
                map.get(fieldConfigSchema.getComponentCode()).add(ToolKit.REFLECT.copyProperties(fieldConfigSchema, FieldConfigSchemaVO.class));
            } else {
                map.get(fieldConfigSchema.getComponentCode()).add(ToolKit.REFLECT.copyProperties(fieldConfigSchema, FieldConfigSchemaVO.class));
            }
        }
        return map;
    }
}
