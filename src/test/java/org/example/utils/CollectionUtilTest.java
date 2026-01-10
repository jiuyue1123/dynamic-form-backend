package org.example.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CollectionUtil 单元测试
 */
@SpringBootTest
class CollectionUtilTest {

    @Test
    void testIsEmptyCollection() {
        // 测试空集合
        assertTrue(ToolKit.COLLECTION.isEmpty((Collection<?>) null));
        assertTrue(ToolKit.COLLECTION.isEmpty(new ArrayList<>()));
        
        // 测试非空集合
        List<String> list = Arrays.asList("a", "b", "c");
        assertFalse(ToolKit.COLLECTION.isEmpty(list));
    }

    @Test
    void testIsNotEmptyCollection() {
        // 测试空集合
        assertFalse(ToolKit.COLLECTION.isNotEmpty((Collection<?>) null));
        assertFalse(ToolKit.COLLECTION.isNotEmpty(new ArrayList<>()));
        
        // 测试非空集合
        List<String> list = Arrays.asList("a", "b", "c");
        assertTrue(ToolKit.COLLECTION.isNotEmpty(list));
    }

    @Test
    void testIsEmptyMap() {
        // 测试空Map
        assertTrue(ToolKit.COLLECTION.isEmpty((Map<?, ?>) null));
        assertTrue(ToolKit.COLLECTION.isEmpty(new HashMap<>()));
        
        // 测试非空Map
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertFalse(ToolKit.COLLECTION.isEmpty(map));
    }

    @Test
    void testIsNotEmptyMap() {
        // 测试空Map
        assertFalse(ToolKit.COLLECTION.isNotEmpty((Map<?, ?>) null));
        assertFalse(ToolKit.COLLECTION.isNotEmpty(new HashMap<>()));
        
        // 测试非空Map
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertTrue(ToolKit.COLLECTION.isNotEmpty(map));
    }

    @Test
    void testDistinct() {
        // 准备测试数据
        List<String> list = Arrays.asList("a", "b", "a", "c", "b", "d");
        
        // 执行测试
        List<String> result = ToolKit.COLLECTION.distinct(list);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("c"));
        assertTrue(result.contains("d"));
    }

    @Test
    void testDistinctWithEmptyList() {
        List<String> emptyList = new ArrayList<>();
        List<String> result = ToolKit.COLLECTION.distinct(emptyList);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testIntersection() {
        // 准备测试数据
        List<String> list1 = Arrays.asList("a", "b", "c", "d");
        List<String> list2 = Arrays.asList("c", "d", "e", "f");
        
        // 执行测试
        List<String> result = ToolKit.COLLECTION.intersection(list1, list2);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("c"));
        assertTrue(result.contains("d"));
    }

    @Test
    void testIntersectionWithNoCommonElements() {
        List<String> list1 = Arrays.asList("a", "b");
        List<String> list2 = Arrays.asList("c", "d");
        
        List<String> result = ToolKit.COLLECTION.intersection(list1, list2);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testIntersectionWithEmptyLists() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = Arrays.asList("a", "b");
        
        List<String> result = ToolKit.COLLECTION.intersection(list1, list2);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUnion() {
        // 准备测试数据
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<String> list2 = Arrays.asList("c", "d", "e");
        
        // 执行测试
        List<String> result = ToolKit.COLLECTION.union(list1, list2);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(5, result.size());
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("c"));
        assertTrue(result.contains("d"));
        assertTrue(result.contains("e"));
    }

    @Test
    void testUnionWithEmptyLists() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = Arrays.asList("a", "b");
        
        List<String> result = ToolKit.COLLECTION.union(list1, list2);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
    }

    @Test
    void testUnionWithBothEmptyLists() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        
        List<String> result = ToolKit.COLLECTION.union(list1, list2);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}