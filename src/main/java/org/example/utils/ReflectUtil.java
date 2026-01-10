package org.example.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author nanak
 *
 * 反射工具类
 */
public class ReflectUtil {
    /**
     * 拷贝对象属性
     */
    public static <T> T copyProperties(Object source, Class<T> target) {
        return BeanUtil.copyProperties(source, target);
    }

    /**
     * 复制对象属性(忽略指定属性)
     */
    public static <T> T copyProperties(Object source, Class<T> target, String... ignoreProperties) {
        return BeanUtil.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 获取对象中 null 值的属性名
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

    /**
     * 获取对象的字段值
     */
    public Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 执行方法
     */
    public Object invokeMethod(Object obj, String methodName, Object[] params) throws NoSuchMethodException, ReflectiveOperationException {
        return cn.hutool.core.util.ReflectUtil.invoke(obj, methodName, params);
    }
}
