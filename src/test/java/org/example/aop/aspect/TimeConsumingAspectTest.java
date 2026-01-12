package org.example.aop.aspect;

import org.example.aop.annotation.TimeConsuming;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nanak
 * 
 * TimeConsumingAspect 单元测试
 */
@ExtendWith(MockitoExtension.class)
class TimeConsumingAspectTest {

    @InjectMocks
    private TimeConsumingAspect timeConsumingAspect;

    @Test
    void testTimeConsumingAnnotation() throws NoSuchMethodException {
        // 测试注解的基本属性
        TimeConsuming timeConsuming = TestService.class.getMethod("testMethod")
                .getAnnotation(TimeConsuming.class);
        
        assertNotNull(timeConsuming);
        assertEquals(1000, timeConsuming.threshold());
    }

    @Test
    void testDefaultTimeConsumingAnnotation() throws NoSuchMethodException {
        // 测试默认注解属性
        TimeConsuming timeConsuming = TestService.class.getMethod("defaultMethod")
                .getAnnotation(TimeConsuming.class);
        
        assertNotNull(timeConsuming);
        assertEquals(500, timeConsuming.threshold());
    }

    /**
     * 测试用的服务类
     */
    static class TestService {
        
        @TimeConsuming(threshold = 1000)
        public String testMethod() {
            return "success";
        }
        
        @TimeConsuming
        public String defaultMethod() {
            return "success";
        }
    }
}