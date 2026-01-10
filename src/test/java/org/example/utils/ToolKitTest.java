package org.example.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ToolKit 单元测试
 */
@SpringBootTest
class ToolKitTest {

    @Test
    void testDateUtilInstance() {
        // 验证DATE工具实例
        assertNotNull(ToolKit.DATE);
        assertTrue(ToolKit.DATE instanceof DateUtil);
        
        // 测试功能
        String now = ToolKit.DATE.now();
        assertNotNull(now);
        assertTrue(now.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    void testCryptoUtilInstance() {
        // 验证CRYPTO工具实例
        assertNotNull(ToolKit.CRYPTO);
        assertTrue(ToolKit.CRYPTO instanceof CryptoUtil);
        
        // 测试功能
        String md5 = ToolKit.CRYPTO.md5("test");
        assertNotNull(md5);
        assertEquals(32, md5.length());
    }

    @Test
    void testJsonUtilInstance() {
        // 验证JSON工具实例
        assertNotNull(ToolKit.JSON);
        assertTrue(ToolKit.JSON instanceof JsonUtil);
        
        // 测试功能
        String json = ToolKit.JSON.toJsonString("test");
        assertNotNull(json);
        assertEquals("\"test\"", json);
    }

    @Test
    void testCollectionUtilInstance() {
        // 验证COLLECTION工具实例
        assertNotNull(ToolKit.COLLECTION);
        assertTrue(ToolKit.COLLECTION instanceof CollectionUtil);
        
        // 测试功能
        assertTrue(ToolKit.COLLECTION.isEmpty((java.util.Collection<?>) null));
    }

    @Test
    void testStringUtilInstance() {
        // 验证STRING工具实例
        assertNotNull(ToolKit.STRING);
        assertTrue(ToolKit.STRING instanceof StringUtil);
        
        // 测试功能
        assertTrue(StringUtil.isEmpty(null));
    }

    @Test
    void testReflectUtilInstance() {
        // 验证REFLECT工具实例
        assertNotNull(ToolKit.REFLECT);
        assertTrue(ToolKit.REFLECT instanceof ReflectUtil);
        
        // 测试功能
        String[] nullProps = ReflectUtil.getNullPropertyNames(new Object());
        assertNotNull(nullProps);
    }

    @Test
    void testAllInstancesAreSingleton() {
        // 验证所有实例都是单例（同一个实例）
        assertSame(ToolKit.DATE, ToolKit.DATE);
        assertSame(ToolKit.CRYPTO, ToolKit.CRYPTO);
        assertSame(ToolKit.JSON, ToolKit.JSON);
        assertSame(ToolKit.COLLECTION, ToolKit.COLLECTION);
        assertSame(ToolKit.STRING, ToolKit.STRING);
        assertSame(ToolKit.REFLECT, ToolKit.REFLECT);
    }

    @Test
    void testIntegratedUsage() {
        // 测试工具类的集成使用
        
        // 1. 创建测试对象
        TestObject obj = new TestObject("张三", 25);
        
        // 2. 使用JSON工具序列化
        String json = ToolKit.JSON.toJsonString(obj);
        assertNotNull(json);
        
        // 3. 使用JSON工具反序列化
        TestObject deserializedObj = ToolKit.JSON.parseObject(json, TestObject.class);
        assertNotNull(deserializedObj);
        assertEquals("张三", deserializedObj.getName());
        assertEquals(25, deserializedObj.getAge());
        
        // 4. 使用加密工具
        String encrypted = ToolKit.CRYPTO.md5(obj.getName());
        assertNotNull(encrypted);
        assertEquals(32, encrypted.length());
        
        // 5. 使用日期工具
        String currentTime = ToolKit.DATE.now();
        assertNotNull(currentTime);
        
        // 6. 使用字符串工具
        String sensitive = StringUtil.sensitive("13812345678");
        assertNotNull(sensitive);
        assertNotEquals("13812345678", sensitive);
    }

    // 测试用的内部类
    public static class TestObject {
        private String name;
        private Integer age;

        public TestObject() {}

        public TestObject(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}