package org.example.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonUtil 单元测试
 */
@SpringBootTest
class JsonUtilTest {

    @Test
    void testParseObject() {
        // 准备测试数据
        String json = "{\"name\":\"张三\",\"age\":25}";
        
        // 执行测试
        TestUser user = ToolKit.JSON.parseObject(json, TestUser.class);
        
        // 验证结果
        assertNotNull(user);
        assertEquals("张三", user.getName());
        assertEquals(25, user.getAge());
    }

    @Test
    void testToJsonString() {
        // 准备测试数据
        TestUser user = new TestUser("李四", 30);
        
        // 执行测试
        String json = ToolKit.JSON.toJsonString(user);
        
        // 验证结果
        assertNotNull(json);
        assertTrue(json.contains("李四"));
        assertTrue(json.contains("30"));
    }

    @Test
    void testToList() {
        // 准备测试数据
        String jsonArray = "[{\"name\":\"张三\",\"age\":25},{\"name\":\"李四\",\"age\":30}]";
        
        // 执行测试
        List<TestUser> users = ToolKit.JSON.toList(jsonArray, TestUser.class);
        
        // 验证结果
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("张三", users.get(0).getName());
        assertEquals("李四", users.get(1).getName());
    }

    @Test
    void testToMap() {
        // 准备测试数据
        String json = "{\"name\":\"张三\",\"age\":25,\"city\":\"北京\"}";
        
        // 执行测试
        Map<String, Object> map = ToolKit.JSON.toMap(json);
        
        // 验证结果
        assertNotNull(map);
        assertEquals("张三", map.get("name"));
        assertEquals(25, map.get("age"));
        assertEquals("北京", map.get("city"));
    }

    @Test
    void testToJsonObject() {
        // 准备测试数据
        TestUser user = new TestUser("王五", 28);
        
        // 执行测试
        var jsonObject = ToolKit.JSON.toJsonObject(user);
        
        // 验证结果
        assertNotNull(jsonObject);
        assertEquals("王五", jsonObject.getString("name"));
        assertEquals(28, jsonObject.getInteger("age"));
    }

    @Test
    void testParseObjectWithNull() {
        // 测试null值
        TestUser user = ToolKit.JSON.parseObject(null, TestUser.class);
        assertNull(user);
    }

    @Test
    void testToJsonStringWithNull() {
        // 测试null值
        String json = ToolKit.JSON.toJsonString(null);
        assertEquals("null", json);
    }

    // 测试用的内部类
    public static class TestUser {
        private String name;
        private Integer age;

        public TestUser() {}

        public TestUser(String name, Integer age) {
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