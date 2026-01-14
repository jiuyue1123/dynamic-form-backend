package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.enums.StatusEnum;
import org.example.pojo.dto.FormSchemaSaveDTO;
import org.example.pojo.entity.FormSchema;
import org.example.pojo.vo.FieldComponentVO;
import org.example.pojo.vo.FormSchemaSaveVO;

import java.util.List;
import java.util.Map;

/**
 * @author nanak
 */
public interface FormSchemaService extends IService<FormSchema> {
    FormSchema getByFormId(String formId);
    List<FormSchema> listFormSchema();
    boolean modifyFormSchemaStatus(String formId, StatusEnum status);
    FormSchemaSaveVO saveFormSchema(FormSchemaSaveDTO formSchemaSaveDTO);
}
