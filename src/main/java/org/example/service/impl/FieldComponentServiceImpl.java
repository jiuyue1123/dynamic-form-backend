package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.mapper.FieldComponentMapper;
import org.example.pojo.entity.FieldComponent;
import org.example.pojo.vo.FieldComponentVO;
import org.example.service.FieldComponentService;
import org.example.utils.ToolKit;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author nanak
 */
@Service
public class FieldComponentServiceImpl extends ServiceImpl<FieldComponentMapper, FieldComponent> implements FieldComponentService {
    @Resource
    private FieldComponentMapper fieldComponentMapper;

    @Override
    public Map<String, List<FieldComponentVO>> listGroupByType() {
        Map<String, List<FieldComponentVO>> map = new LinkedHashMap<>();

        QueryWrapper<FieldComponent> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        for (FieldComponent fieldComponent : this.list(queryWrapper)) {
            // 判断component_type字段
            if (!map.containsKey(fieldComponent.getComponentType().getValue())) {
                map.put(fieldComponent.getComponentType().getValue(), new ArrayList<>());
                map.get(fieldComponent.getComponentType().getValue()).add(ToolKit.REFLECT.copyProperties(fieldComponent, FieldComponentVO.class));
            } else {
                map.get(fieldComponent.getComponentType().getValue()).add(ToolKit.REFLECT.copyProperties(fieldComponent, FieldComponentVO.class));
            }
        }
        return map;
    }
}
