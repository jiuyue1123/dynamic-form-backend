package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.enums.ErrorCode;
import org.example.enums.StatusEnum;
import org.example.exception.BusinessException;
import org.example.mapper.FormSchemaMapper;
import org.example.pojo.dto.FormSchemaSaveDTO;
import org.example.pojo.entity.FormSchema;
import org.example.pojo.vo.FormSchemaSaveVO;
import org.example.service.FormSchemaService;
import org.example.utils.ToolKit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author nanak
 */
@Service
public class FormSchemaServiceImpl extends ServiceImpl<FormSchemaMapper, FormSchema> implements FormSchemaService {
    @Resource
    private FormSchemaMapper formSchemaMapper;

    @Override
    public FormSchema getByFormId(String formId) {
        QueryWrapper<FormSchema> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("form_id", formId);
        return formSchemaMapper.selectOne(queryWrapper);
    }

    @Override
    public List<FormSchema> listFormSchema() {
        return this.list();
    }

    @Override
    public boolean modifyFormSchemaStatus(String formId, StatusEnum status) {
        FormSchema formSchema = this.getByFormId(formId);
        if (formSchema != null) {
            formSchema.setStatus(status);
            return this.updateById(formSchema);
        }
        throw new BusinessException(ErrorCode.NOT_FOUND.getCode(), "form is not exist");
    }

    @Override
    public FormSchemaSaveVO saveFormSchema(FormSchemaSaveDTO formSchemaSaveDTO) {
        FormSchema formSchema = FormSchema.builder()
                .formId(UUID.randomUUID().toString())
                .formName(formSchemaSaveDTO.getFormName())
                // 默认版本号
                .schemaVersion("v1.0")
                .formDesc(formSchemaSaveDTO.getFormDesc())
                .fields(formSchemaSaveDTO.getFields())
                .status(formSchemaSaveDTO.getStatus())
                .build();
        boolean save = this.save(formSchema);
        if (save) {
            return ToolKit.REFLECT.copyProperties(formSchema, FormSchemaSaveVO.class);
        }
        throw new BusinessException(ErrorCode.OPERATION_ERROR.getCode(), "save form schema failed");
    }

}
