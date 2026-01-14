package org.example.controller;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.example.enums.StatusEnum;
import org.example.pojo.dto.FormSchemaSaveDTO;
import org.example.pojo.entity.FormSchema;
import org.example.result.Result;
import org.example.service.FormSchemaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author nanak
 */
@RestController
@RequestMapping("/formSchema")
public class FormSchemaController {
    @Resource
    private FormSchemaService formSchemaService;

    @GetMapping
    public Result getFormSchemaByFormId(@Validated @NotEmpty @RequestParam String formId) {
        FormSchema formSchema = formSchemaService.getByFormId(formId);
        return Result.success(formSchema);
    }

    @GetMapping("/list")
    public Result list() {
        return Result.success(formSchemaService.listFormSchema());
    }

    @PostMapping
    public Result save(@Validated @RequestBody FormSchemaSaveDTO formSchemaSaveDTO) {
        return Result.success(formSchemaService.saveFormSchema(formSchemaSaveDTO));
    }

    @PutMapping("/{formId}/{status}")
    public Result modifyFormSchemaStatus(@PathVariable String formId, @PathVariable StatusEnum status) {
        formSchemaService.modifyFormSchemaStatus(formId, status);
        return Result.success();
    }
}
