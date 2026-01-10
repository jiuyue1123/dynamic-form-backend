package org.example.exception;

import org.example.enums.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ThrowUtils 单元测试
 */
@SpringBootTest
class ThrowUtilsTest {

    @Test
    void testThrowIfWithRuntimeException_ConditionTrue() {
        // 准备测试数据
        RuntimeException exception = new RuntimeException("测试异常");
        
        // 执行测试并验证异常
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            ThrowUtils.throwIf(true, exception);
        });
        
        // 验证异常信息
        assertEquals("测试异常", thrown.getMessage());
        assertSame(exception, thrown);
    }

    @Test
    void testThrowIfWithRuntimeException_ConditionFalse() {
        // 准备测试数据
        RuntimeException exception = new RuntimeException("测试异常");
        
        // 执行测试，条件为false，不应该抛出异常
        assertDoesNotThrow(() -> {
            ThrowUtils.throwIf(false, exception);
        });
    }

    @Test
    void testThrowIfWithErrorCode_ConditionTrue() {
        // 执行测试并验证异常
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(true, ErrorCode.PARAMS_ERROR);
        });
        
        // 验证异常信息
        assertEquals(ErrorCode.PARAMS_ERROR.getCode(), thrown.getCode());
        assertEquals(ErrorCode.PARAMS_ERROR.getMessage(), thrown.getMessage());
    }

    @Test
    void testThrowIfWithErrorCode_ConditionFalse() {
        // 执行测试，条件为false，不应该抛出异常
        assertDoesNotThrow(() -> {
            ThrowUtils.throwIf(false, ErrorCode.PARAMS_ERROR);
        });
    }

    @Test
    void testThrowIfWithDifferentErrorCodes() {
        // 测试不同的错误码
        
        // 测试UNAUTHORIZED
        BusinessException unauthorizedException = assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(true, ErrorCode.UNAUTHORIZED);
        });
        assertEquals(ErrorCode.UNAUTHORIZED.getCode(), unauthorizedException.getCode());
        assertEquals(ErrorCode.UNAUTHORIZED.getMessage(), unauthorizedException.getMessage());
        
        // 测试FORBIDDEN
        BusinessException forbiddenException = assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(true, ErrorCode.FORBIDDEN);
        });
        assertEquals(ErrorCode.FORBIDDEN.getCode(), forbiddenException.getCode());
        assertEquals(ErrorCode.FORBIDDEN.getMessage(), forbiddenException.getMessage());
        
        // 测试NOT_FOUND
        BusinessException notFoundException = assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(true, ErrorCode.NOT_FOUND);
        });
        assertEquals(ErrorCode.NOT_FOUND.getCode(), notFoundException.getCode());
        assertEquals(ErrorCode.NOT_FOUND.getMessage(), notFoundException.getMessage());
        
        // 测试SERVER_ERROR
        BusinessException serverErrorException = assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(true, ErrorCode.SERVER_ERROR);
        });
        assertEquals(ErrorCode.SERVER_ERROR.getCode(), serverErrorException.getCode());
        assertEquals(ErrorCode.SERVER_ERROR.getMessage(), serverErrorException.getMessage());
    }

    @Test
    void testThrowIfWithCustomRuntimeException() {
        // 测试自定义运行时异常
        IllegalArgumentException customException = new IllegalArgumentException("自定义参数异常");
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ThrowUtils.throwIf(true, customException);
        });
        
        assertEquals("自定义参数异常", thrown.getMessage());
        assertSame(customException, thrown);
    }

    @Test
    void testThrowIfWithNullException() {
        // 测试null异常
        assertThrows(NullPointerException.class, () -> {
            ThrowUtils.throwIf(true, (RuntimeException) null);
        });
    }

    @Test
    void testThrowIfWithNullErrorCode() {
        // 测试null错误码
        assertThrows(NullPointerException.class, () -> {
            ThrowUtils.throwIf(true, (ErrorCode) null);
        });
    }

    @Test
    void testPracticalUsageScenarios() {
        // 实际使用场景测试
        
        // 场景1：参数验证
        String username = null;
        assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(username == null, ErrorCode.PARAMS_ERROR);
        });
        
        // 场景2：权限检查
        boolean hasPermission = false;
        assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(!hasPermission, ErrorCode.FORBIDDEN);
        });
        
        // 场景3：资源不存在
        Object resource = null;
        assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(resource == null, ErrorCode.NOT_FOUND);
        });
        
        // 场景4：正常情况不抛异常
        String validUsername = "testUser";
        boolean validPermission = true;
        Object validResource = new Object();
        
        assertDoesNotThrow(() -> {
            ThrowUtils.throwIf(validUsername == null, ErrorCode.PARAMS_ERROR);
            ThrowUtils.throwIf(!validPermission, ErrorCode.FORBIDDEN);
            ThrowUtils.throwIf(validResource == null, ErrorCode.NOT_FOUND);
        });
    }

    @Test
    void testChainedThrowIf() {
        // 测试链式调用
        String param1 = "valid";
        String param2 = null;
        
        // 第一个条件为false，不抛异常
        // 第二个条件为true，抛异常
        assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(param1 == null, ErrorCode.PARAMS_ERROR);
            ThrowUtils.throwIf(param2 == null, ErrorCode.PARAMS_ERROR);
        });
    }

    @Test
    void testComplexConditions() {
        // 测试复杂条件
        int age = 15;
        String email = "invalid-email";
        
        // 测试复合条件
        assertThrows(BusinessException.class, () -> {
            ThrowUtils.throwIf(age < 18 || !email.contains("@"), ErrorCode.PARAMS_ERROR);
        });
        
        // 测试正常情况
        int validAge = 25;
        String validEmail = "test@example.com";
        
        assertDoesNotThrow(() -> {
            ThrowUtils.throwIf(validAge < 18 || !validEmail.contains("@"), ErrorCode.PARAMS_ERROR);
        });
    }
}