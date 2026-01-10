package org.example.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author nanak
 * <p>
 * 集合工具类(封装hutool)
 */
public class CollectionUtil {
    /**
     * 判断集合是否为空
     */
    public boolean isEmpty(Collection<?> collection) {
        return CollUtil.isEmpty(collection);
    }

    /**
     * 判断集合是否非空
     */
    public boolean isNotEmpty(Collection<?> collection) {
        return CollUtil.isNotEmpty(collection);
    }

    /**
     * 判断Map是否为空
     */
    public boolean isEmpty(Map<?, ?> map) {
        return CollUtil.isEmpty(map);
    }

    /**
     * 判断Map是否非空
     */
    public boolean isNotEmpty(Map<?, ?> map) {
        return CollUtil.isNotEmpty(map);
    }

    /**
     * 列表去重
     */
    public <T> List<T> distinct(List<T> list) {
        return CollUtil.distinct(list);
    }

    /**
     * 两个集合的交集
     */
    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        return (List<T>) CollUtil.intersection(list1, list2);
    }

    /**
     * 两个集合的并集
     */
    public <T> List<T> union(List<T> list1, List<T> list2) {
        return (List<T>) CollUtil.union(list1, list2);
    }
}
