package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.example.enums.StatusEnum;
import org.example.pojo.dto.FormDataQueryDTO;
import org.example.pojo.dto.FormDataSaveDTO;
import org.example.pojo.dto.FormDataUpdateDTO;
import org.example.pojo.vo.FormDataVO;
import org.example.result.Result;
import org.example.service.FormDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author nanak
 *
 * 表单数据Controller
 */
@RestController
@RequestMapping("/formData")
@Tag(name = "FormData", description = "表单数据管理接口")
public class FormDataController {
    
    @Resource
    private FormDataService formDataService;
    
    @PostMapping
    @Operation(summary = "保存表单数据", description = "提交新的表单数据")
    public Result<FormDataVO> save(@Validated @RequestBody FormDataSaveDTO formDataSaveDTO) {
        FormDataVO formDataVO = formDataService.saveFormData(formDataSaveDTO);
        return Result.success(formDataVO);
    }
    
    @PutMapping
    @Operation(summary = "更新表单数据", description = "更新已存在的表单数据")
    public Result<Boolean> update(@Validated @RequestBody FormDataUpdateDTO formDataUpdateDTO) {
        boolean result = formDataService.updateFormData(formDataUpdateDTO);
        return Result.success(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取表单数据", description = "通过表单数据ID查询详情")
    public Result<FormDataVO> getById(@PathVariable @NotNull Long id) {
        FormDataVO formDataVO = formDataService.getFormDataById(id);
        return Result.success(formDataVO);
    }
    
    @GetMapping("/list/{formId}")
    @Operation(summary = "根据formId获取表单数据列表", description = "查询指定表单的所有提交数据")
    public Result<List<FormDataVO>> listByFormId(@PathVariable String formId) {
        List<FormDataVO> list = formDataService.listByFormId(formId);
        return Result.success(list);
    }
    
    @PostMapping("/page")
    @Operation(summary = "分页查询表单数据", description = "根据条件分页查询表单数据")
    public Result<Page<FormDataVO>> page(@RequestBody FormDataQueryDTO formDataQueryDTO) {
        Page<FormDataVO> page = formDataService.pageFormData(formDataQueryDTO);
        return Result.success(page);
    }
    
    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "修改表单数据状态", description = "修改表单数据的启用/禁用状态")
    public Result<Boolean> modifyStatus(@PathVariable Long id, @PathVariable StatusEnum status) {
        boolean result = formDataService.modifyFormDataStatus(id, status);
        return Result.success(result);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除表单数据", description = "逻辑删除表单数据")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = formDataService.deleteFormData(id);
        return Result.success(result);
    }
}
