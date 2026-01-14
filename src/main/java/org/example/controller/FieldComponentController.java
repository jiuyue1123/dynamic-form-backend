package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.pojo.entity.FieldComponent;
import org.example.result.Result;
import org.example.service.FieldComponentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nanak
 */
@RestController
@RequestMapping("/fieldComponent")
public class FieldComponentController {
    @Resource
    private FieldComponentService fieldComponentService;
    @GetMapping("/list")
    public Result list() {
        QueryWrapper<FieldComponent> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        return Result.success(fieldComponentService.list(queryWrapper));
    }

    @GetMapping("/groupByType")
    public Result groupByType() {
        return Result.success(fieldComponentService.listGroupByType());
    }
}
