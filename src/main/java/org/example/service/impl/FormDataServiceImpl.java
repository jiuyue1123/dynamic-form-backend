package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.enums.ErrorCode;
import org.example.enums.StatusEnum;
import org.example.exception.BusinessException;
import org.example.exception.ThrowUtils;
import org.example.mapper.FormDataMapper;
import org.example.pojo.dto.FormDataQueryDTO;
import org.example.pojo.dto.FormDataSaveDTO;
import org.example.pojo.dto.FormDataUpdateDTO;
import org.example.pojo.entity.FormData;
import org.example.pojo.vo.FormDataVO;
import org.example.service.FormDataService;
import org.example.utils.ToolKit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nanak
 *
 * 表单数据Service实现类
 */
@Service
public class FormDataServiceImpl extends ServiceImpl<FormDataMapper, FormData> implements FormDataService {
    
    @Resource
    private FormDataMapper formDataMapper;
    
    @Override
    public FormDataVO saveFormData(FormDataSaveDTO formDataSaveDTO) {
        FormData formData = FormData.builder()
                .formId(formDataSaveDTO.getFormId())
                .schemaVersion(formDataSaveDTO.getSchemaVersion())
                .formDataJson(formDataSaveDTO.getFormDataJson())
                .status(formDataSaveDTO.getStatus())
                .extInfo(formDataSaveDTO.getExtInfo())
                .build();
        
        boolean save = this.save(formData);
        ThrowUtils.throwIf(!save, ErrorCode.OPERATION_ERROR, "save form data failed");
        
        return ToolKit.REFLECT.copyProperties(formData, FormDataVO.class);
    }
    
    @Override
    public boolean updateFormData(FormDataUpdateDTO formDataUpdateDTO) {
        FormData formData = formDataMapper.selectById(formDataUpdateDTO.getId());
        ThrowUtils.throwIf(formData == null, ErrorCode.NOT_FOUND, "form data not found");
        
        formData.setFormDataJson(formDataUpdateDTO.getFormDataJson());
        if (formDataUpdateDTO.getStatus() != null) {
            formData.setStatus(formDataUpdateDTO.getStatus());
        }
        if (formDataUpdateDTO.getExtInfo() != null) {
            formData.setExtInfo(formDataUpdateDTO.getExtInfo());
        }
        
        return this.updateById(formData);
    }
    
    @Override
    public FormDataVO getFormDataById(Long id) {
        FormData formData = formDataMapper.selectById(id);
        ThrowUtils.throwIf(formData == null, ErrorCode.NOT_FOUND, "form data not found");
        
        return ToolKit.REFLECT.copyProperties(formData, FormDataVO.class);
    }
    
    @Override
    public List<FormDataVO> listByFormId(String formId) {
        QueryWrapper<FormData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("form_id", formId);
        queryWrapper.orderByDesc("submit_time");
        
        List<FormData> formDataList = formDataMapper.selectList(queryWrapper);
        return formDataList.stream()
                .map(formData -> ToolKit.REFLECT.copyProperties(formData, FormDataVO.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<FormDataVO> pageFormData(FormDataQueryDTO formDataQueryDTO) {
        int pageNum = formDataQueryDTO.getPageNum() != null ? formDataQueryDTO.getPageNum() : 1;
        int pageSize = formDataQueryDTO.getPageSize() != null ? formDataQueryDTO.getPageSize() : 10;
        
        Page<FormData> page = new Page<>(pageNum, pageSize);
        QueryWrapper<FormData> queryWrapper = new QueryWrapper<>();
        
        if (formDataQueryDTO.getFormId() != null) {
            queryWrapper.eq("form_id", formDataQueryDTO.getFormId());
        }
        if (formDataQueryDTO.getSchemaVersion() != null) {
            queryWrapper.eq("schema_version", formDataQueryDTO.getSchemaVersion());
        }
        if (formDataQueryDTO.getStatus() != null) {
            queryWrapper.eq("status", formDataQueryDTO.getStatus());
        }
        if (formDataQueryDTO.getSubmitTimeStart() != null) {
            queryWrapper.ge("submit_time", formDataQueryDTO.getSubmitTimeStart());
        }
        if (formDataQueryDTO.getSubmitTimeEnd() != null) {
            queryWrapper.le("submit_time", formDataQueryDTO.getSubmitTimeEnd());
        }
        
        queryWrapper.orderByDesc("submit_time");
        
        Page<FormData> formDataPage = formDataMapper.selectPage(page, queryWrapper);
        
        Page<FormDataVO> voPage = new Page<>(formDataPage.getCurrent(), formDataPage.getSize(), formDataPage.getTotal());
        List<FormDataVO> voList = formDataPage.getRecords().stream()
                .map(formData -> ToolKit.REFLECT.copyProperties(formData, FormDataVO.class))
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    public boolean modifyFormDataStatus(Long id, StatusEnum status) {
        FormData formData = formDataMapper.selectById(id);
        ThrowUtils.throwIf(formData == null, ErrorCode.NOT_FOUND, "form data not found");
        
        formData.setStatus(status);
        return this.updateById(formData);
    }
    
    @Override
    public boolean deleteFormData(Long id) {

        return formDataMapper.deleteById(id) > 0;
    }
}
