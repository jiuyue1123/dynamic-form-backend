package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.dto.FieldConfigSchemaQueryDTO;
import org.example.pojo.entity.FieldConfigSchema;
import org.example.result.Result;
import org.example.service.FieldConfigSchemaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author nanak
 */
@Slf4j
@RestController
@RequestMapping("/fieldConfigSchema")
public class FieldConfigSchemaController {
    @Resource
    private FieldConfigSchemaService fieldConfigSchemaService;
    @GetMapping("/list")
    public Result list() {
        QueryWrapper<FieldConfigSchema> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        return Result.success(fieldConfigSchemaService.list());
    }

    @GetMapping("/groupByComponentCode")
    public Result groupByType() {
        return Result.success(fieldConfigSchemaService.listGroupByComponentCode());
    }

    @GetMapping
    public Result listComponent(@Validated @ModelAttribute FieldConfigSchemaQueryDTO fieldConfigSchemaQueryDTO) {
        log.info("fieldConfigSchemaQueryDTO: {}", fieldConfigSchemaQueryDTO);
        QueryWrapper<FieldConfigSchema> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("component_code", fieldConfigSchemaQueryDTO.getComponentCode());
        queryWrapper.orderByAsc("sort");
        return Result.success(fieldConfigSchemaService.list(queryWrapper));
    }
}
