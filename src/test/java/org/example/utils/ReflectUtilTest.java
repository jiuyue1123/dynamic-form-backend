package org.example.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ReflectUtil 单元测试
 */
@SpringBootTest
class ReflectUtilTest {

    @Test
    void testCopyProperties() {
        // 准备测试数据
        SourceObject source = new SourceObject("张三", 25, "北京");
        
        // 执行测试
        TargetObject target = ToolKit.REFLECT.copyProperties(source, TargetObject.class);
        
        // 验证结果
        assertNotNull(target);
        assertEquals("张三", target.getName());
        assertEquals(25, target.getAge());
        assertEquals("北京", target.getCity());
    }

    @Test
    void testCopyPropertiesWithIgnore() {
        // 准备测试数据
        SourceObject source = new SourceObject("李四", 30, "上海");
        
        // 执行测试，忽略age属性
        TargetObject target = ToolKit.REFLECT.copyProperties(source, TargetObject.class, "age");
        
        // 验证结果
        assertNotNull(target);
        assertEquals("李四", target.getName());
        assertNull(target.getAge()); // 被忽略的属性应该为null
        assertEquals("上海", target.getCity());
    }

    @Test
    void testCopyPropertiesWithNullSource() {
        // 测试null源对象
        TargetObject target = ToolKit.REFLECT.copyProperties(null, TargetObject.class);
        assertNull(target);
    }

    @Test
    void testGetNullPropertyNames() {
        // 准备测试数据
        SourceObject obj = new SourceObject("王五", null, "广州");
        
        // 执行测试
        String[] nullProperties = ToolKit.REFLECT.getNullPropertyNames(obj);
        
        // 验证结果
        assertNotNull(nullProperties);
        assertTrue(nullProperties.length > 0);
        
        // 检查是否包含age属性（因为它是null）
        boolean containsAge = false;
        for (String prop : nullProperties) {
            if ("age".equals(prop)) {
                containsAge = true;
                break;
            }
        }
        assertTrue(containsAge);
    }

    @Test
    void testGetNullPropertyNamesWithAllNonNull() {
        // 准备测试数据
        SourceObject obj = new SourceObject("赵六", 28, "深圳");
        
        // 执行测试
        String[] nullProperties = ToolKit.REFLECT.getNullPropertyNames(obj);
        
        // 验证结果
        assertNotNull(nullProperties);
        // 应该只包含class属性（所有对象都有的属性）
        boolean hasOnlyClassProperty = true;
        for (String prop : nullProperties) {
            if (!"class".equals(prop)) {
                hasOnlyClassProperty = false;
                break;
            }
        }
        // 注意：可能还有其他系统属性，所以这里只检查不包含我们的业务属性
        boolean containsBusinessProperty = false;
        for (String prop : nullProperties) {
            if ("name".equals(prop) || "age".equals(prop) || "city".equals(prop)) {
                containsBusinessProperty = true;
                break;
            }
        }
        assertFalse(containsBusinessProperty);
    }

    @Test
    void testGetFieldValue() throws NoSuchFieldException, IllegalAccessException {
        // 准备测试数据
        SourceObject obj = new SourceObject("田七", 35, "杭州");
        
        // 执行测试
        Object nameValue = ToolKit.REFLECT.getFieldValue(obj, "name");
        Object ageValue = ToolKit.REFLECT.getFieldValue(obj, "age");
        
        // 验证结果
        assertEquals("田七", nameValue);
        assertEquals(35, ageValue);
    }

    @Test
    void testGetFieldValueWithNonExistentField() {
        // 准备测试数据
        SourceObject obj = new SourceObject("测试", 20, "测试城市");
        
        // 执行测试并验证异常
        assertThrows(NoSuchFieldException.class, () -> {
            ToolKit.REFLECT.getFieldValue(obj, "nonExistentField");
        });
    }

    @Test
    void testInvokeMethod() throws ReflectiveOperationException {
        // 准备测试数据
        SourceObject obj = new SourceObject("测试", 25, "测试城市");
        
        // 执行测试
        Object result = ToolKit.REFLECT.invokeMethod(obj, "getName", new Object[]{});
        
        // 验证结果
        assertEquals("测试", result);
    }

    @Test
    void testInvokeMethodWithParameters() throws ReflectiveOperationException {
        // 准备测试数据
        SourceObject obj = new SourceObject("原名", 25, "原城市");
        
        // 执行测试
        ToolKit.REFLECT.invokeMethod(obj, "setName", new Object[]{"新名字"});
        
        // 验证结果
        assertEquals("新名字", obj.getName());
    }

    @Test
    void testInvokeMethodWithNonExistentMethod() {
        // 准备测试数据
        SourceObject obj = new SourceObject("测试", 25, "测试城市");
        
        // 执行测试并验证异常
        assertThrows(Exception.class, () -> {
            ToolKit.REFLECT.invokeMethod(obj, "nonExistentMethod", new Object[]{});
        });
    }

    // 测试用的源对象类
    public static class SourceObject {
        private String name;
        private Integer age;
        private String city;

        public SourceObject() {}

        public SourceObject(String name, Integer age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    // 测试用的目标对象类
    public static class TargetObject {
        private String name;
        private Integer age;
        private String city;

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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}