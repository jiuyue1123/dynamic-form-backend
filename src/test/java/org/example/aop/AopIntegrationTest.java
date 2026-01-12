package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.example.aop.annotation.Idempotent;
import org.example.aop.annotation.TimeConsuming;
import org.example.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AOP 集成测试
 * 测试幂等性和耗时监控功能
 */
@Slf4j
@SpringBootTest
class AopIntegrationTest {

    @Autowired
    private TestService testService;

    @Test
    void testIdempotentSuccess() {
        // 第一次调用应该成功
        String result1 = testService.idempotentMethod("user123", "action1");
        assertEquals("success", result1);
    }

    @Test
    void testIdempotentDuplicateRequest() {
        String userId = "user" + System.currentTimeMillis();
        
        // 第一次调用应该成功
        String result1 = testService.idempotentMethod(userId, "duplicateTest");
        assertEquals("success", result1);
        
        // 立即第二次调用应该抛出异常
        assertThrows(BusinessException.class, () -> {
            testService.idempotentMethod(userId, "duplicateTest");
        });
    }

    @Test
    void testTimeConsumingNormalExecution() {
        // 正常执行时间，不应该抛出异常
        assertDoesNotThrow(() -> {
            String result = testService.timeConsumingMethod(200);
            assertEquals("completed", result);
        });
    }

    @Test
    void testTimeConsumingExceedsThreshold() {
        // 超过阈值的执行时间，不应该抛出异常，但会记录警告日志
        assertDoesNotThrow(() -> {
            String result = testService.timeConsumingMethod(600);
            assertEquals("completed", result);
        });
    }

    @Test
    void testCombinedAopFeatures() {
        String testId = "combined" + System.currentTimeMillis();
        
        // 第一次调用应该成功
        String result1 = testService.combinedMethod(testId, 300);
        assertEquals("combined success", result1);
        
        // 立即第二次调用应该被幂等性拦截
        assertThrows(BusinessException.class, () -> {
            testService.combinedMethod(testId, 300);
        });
    }

    /**
     * 测试配置类
     */
    @TestConfiguration
    static class TestConfig {
        
        @Bean
        public TestService testService() {
            return new TestService();
        }
    }

    /**
     * 测试服务类
     */
    static class TestService {
        
        @Idempotent(value = "userId", message = "Duplicate request detected")
        public String idempotentMethod(String userId, String action) {
            log.info("Executing idempotent method for user: {}, action: {}", userId, action);
            return "success";
        }
        
        @TimeConsuming(threshold = 500)
        public String timeConsumingMethod(int sleepTime) {
            log.info("Executing time consuming method with sleep: {}ms", sleepTime);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "completed";
        }
        
        @Idempotent(value = "testId", message = "Combined method duplicate request")
        @TimeConsuming(threshold = 400)
        public String combinedMethod(String testId, int sleepTime) {
            log.info("Executing combined method for testId: {}, sleep: {}ms", testId, sleepTime);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "combined success";
        }
    }
}