package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.enums.StatusEnum;
import org.example.pojo.dto.FormDataQueryDTO;
import org.example.pojo.dto.FormDataSaveDTO;
import org.example.pojo.dto.FormDataUpdateDTO;
import org.example.pojo.entity.FormData;
import org.example.pojo.vo.FormDataVO;

import java.util.List;

/**
 * @author nanak
 *
 * 表单数据Service接口
 */
public interface FormDataService extends IService<FormData> {
    
    /**
     * 保存表单数据
     * @param formDataSaveDTO 表单数据保存DTO
     * @return 表单数据VO
     */
    FormDataVO saveFormData(FormDataSaveDTO formDataSaveDTO);
    
    /**
     * 更新表单数据
     * @param formDataUpdateDTO 表单数据更新DTO
     * @return 是否更新成功
     */
    boolean updateFormData(FormDataUpdateDTO formDataUpdateDTO);
    
    /**
     * 根据ID获取表单数据
     * @param id 表单数据ID
     * @return 表单数据VO
     */
    FormDataVO getFormDataById(Long id);
    
    /**
     * 根据formId获取表单数据列表
     * @param formId 表单ID
     * @return 表单数据列表
     */
    List<FormDataVO> listByFormId(String formId);
    
    /**
     * 分页查询表单数据
     * @param formDataQueryDTO 查询条件
     * @return 分页结果
     */
    Page<FormDataVO> pageFormData(FormDataQueryDTO formDataQueryDTO);
    
    /**
     * 修改表单数据状态
     * @param id 表单数据ID
     * @param status 状态
     * @return 是否修改成功
     */
    boolean modifyFormDataStatus(Long id, StatusEnum status);
    
    /**
     * 删除表单数据（逻辑删除）
     * @param id 表单数据ID
     * @return 是否删除成功
     */
    boolean deleteFormData(Long id);
}
