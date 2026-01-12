package org.example.aop.aspect;

import org.example.aop.annotation.Idempotent;
import org.example.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nanak
 * 
 * IdempotentAspect 单元测试
 */
@ExtendWith(MockitoExtension.class)
class IdempotentAspectTest {

    @InjectMocks
    private IdempotentAspect idempotentAspect;

    @BeforeEach
    void setUp() {
        // 清空缓存
        ConcurrentHashMap<String, Long> requestCache = new ConcurrentHashMap<>();
        ReflectionTestUtils.setField(idempotentAspect, "requestCache", requestCache);
    }

    @Test
    void testIdempotentAnnotation() {
        // 测试注解的基本属性
        try {
            Idempotent idempotent = TestService.class.getMethod("testMethod", String.class)
                    .getAnnotation(Idempotent.class);
            assertNotNull(idempotent);
            assertEquals("userId", idempotent.value());
            assertEquals("请勿重复提交", idempotent.message());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDefaultIdempotentAnnotation() throws NoSuchMethodException {
        // 测试默认注解属性
        Idempotent idempotent = TestService.class.getMethod("defaultMethod")
                .getAnnotation(Idempotent.class);
        
        assertNotNull(idempotent);
        assertEquals("key", idempotent.value());
        assertEquals("too many requests, please try again later", idempotent.message());
    }

    /**
     * 测试用的服务类
     */
    static class TestService {
        
        @Idempotent(value = "userId", message = "请勿重复提交")
        public String testMethod(String userId) {
            return "success";
        }
        
        @Idempotent
        public String defaultMethod() {
            return "success";
        }
    }
}