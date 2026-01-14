package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.entity.FormData;

/**
 * @author nanak
 *
 * 表单数据Mapper
 */
@Mapper
public interface FormDataMapper extends BaseMapper<FormData> {
}
