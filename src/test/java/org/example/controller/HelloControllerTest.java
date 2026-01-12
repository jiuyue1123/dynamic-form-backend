package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.HelloController.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * HelloController 单元测试
 * 使用 @SpringBootTest 以支持 AOP 功能测试
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isString());
    }

    @Test
    void testShowTools() throws Exception {
        mockMvc.perform(get("/api/hello/tools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.currentTime").exists())
                .andExpect(jsonPath("$.data.md5Hash").exists())
                .andExpect(jsonPath("$.data.randomCode").exists())
                .andExpect(jsonPath("$.data.jsonExample").exists())
                .andExpect(jsonPath("$.data.sensitiveDataMasked").exists())
                .andExpect(jsonPath("$.data.jwtToken").exists())
                .andExpect(jsonPath("$.data.tokenValid").value(true));
    }

    @Test
    void testValidateParamsSuccess() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername("张三");
        request.setPhone("13812345678");

        mockMvc.perform(post("/api/hello/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    void testValidateParamsFailure() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername(""); // 空用户名
        request.setPhone("invalid-phone"); // 无效手机号

        mockMvc.perform(post("/api/hello/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40000)); // 参数错误码
    }

    @Test
    void testBusinessErrorNoException() throws Exception {
        mockMvc.perform(get("/api/hello/error/business")
                        .param("throwError", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value("没有抛出异常"));
    }

    @Test
    void testBusinessErrorWithException() throws Exception {
        mockMvc.perform(get("/api/hello/error/business")
                        .param("throwError", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40000)); // 参数错误码
    }

    @Test
    void testRuntimeErrorNoException() throws Exception {
        mockMvc.perform(get("/api/hello/error/runtime")
                        .param("throwError", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value("没有抛出异常"));
    }

    @Test
    void testRuntimeErrorWithException() throws Exception {
        mockMvc.perform(get("/api/hello/error/runtime")
                        .param("throwError", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(50000)); // 服务器错误码
    }

    @Test
    void testValidationErrorSuccess() throws Exception {
        mockMvc.perform(get("/api/hello/error/validation")
                        .param("username", "张三")
                        .param("phone", "13812345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value("参数校验通过"));
    }

    @Test
    void testValidationErrorFailure() throws Exception {
        mockMvc.perform(get("/api/hello/error/validation")
                        .param("username", "") // 空用户名
                        .param("phone", "invalid")) // 无效手机号
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40000)); // 参数错误码
    }

    @Test
    void testShowTrace() throws Exception {
        mockMvc.perform(get("/api/hello/trace"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.traceId").exists())
                .andExpect(jsonPath("$.data.message").exists());
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(get("/api/hello/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.status").value("UP"))
                .andExpect(jsonPath("$.data.timestamp").exists())
                .andExpect(jsonPath("$.data.version").value("1.0.0"))
                .andExpect(jsonPath("$.data.traceId").exists());
    }

    @Test
    void testIdempotentSuccess() throws Exception {
        mockMvc.perform(post("/api/hello/idempotent")
                        .param("userId", "12345")
                        .param("action", "createOrder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isString());
    }

    @Test
    void testIdempotentDuplicateRequest() throws Exception {
        // 第一次请求应该成功
        mockMvc.perform(post("/api/hello/idempotent")
                        .param("userId", "99999")
                        .param("action", "testDuplicate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        // 立即发送相同请求，应该被幂等性拦截
        mockMvc.perform(post("/api/hello/idempotent")
                        .param("userId", "99999")
                        .param("action", "testDuplicate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40500)) // OPERATION_ERROR
                .andExpect(jsonPath("$.message").value("请勿重复提交，请稍后再试"));
    }

    @Test
    void testTimeConsumingNormalExecution() throws Exception {
        // 测试正常执行时间（不超过阈值）
        mockMvc.perform(get("/api/hello/time-consuming")
                        .param("sleepTime", "300"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.requestedSleepTime").value(300))
                .andExpect(jsonPath("$.data.threshold").value(800))
                .andExpect(jsonPath("$.data.exceedsThreshold").value(false));
    }

    @Test
    void testTimeConsumingExceedsThreshold() throws Exception {
        // 测试超过阈值的执行时间
        mockMvc.perform(get("/api/hello/time-consuming")
                        .param("sleepTime", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.requestedSleepTime").value(1000))
                .andExpect(jsonPath("$.data.threshold").value(800))
                .andExpect(jsonPath("$.data.exceedsThreshold").value(true));
    }

    @Test
    void testAopDemoSuccess() throws Exception {
        mockMvc.perform(post("/api/hello/aop-demo")
                        .param("demoId", "demo123")
                        .param("processTime", "800"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.demoId").value("demo123"))
                .andExpect(jsonPath("$.data.requestedProcessTime").value(800))
                .andExpect(jsonPath("$.data.timeThreshold").value(1000))
                .andExpect(jsonPath("$.data.idempotentWindow").value("5秒"))
                .andExpect(jsonPath("$.data.features").isArray())
                .andExpect(jsonPath("$.data.traceId").exists())
                .andExpect(jsonPath("$.data.timestamp").exists());
    }

    @Test
    void testAopDemoExceedsTimeThreshold() throws Exception {
        mockMvc.perform(post("/api/hello/aop-demo")
                        .param("demoId", "demo456")
                        .param("processTime", "1500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.demoId").value("demo456"))
                .andExpect(jsonPath("$.data.requestedProcessTime").value(1500))
                .andExpect(jsonPath("$.data.exceedsTimeThreshold").value(true));
    }

    @Test
    void testAopDemoDuplicateRequest() throws Exception {
        String demoId = "duplicateDemo" + System.currentTimeMillis();
        
        // 第一次请求应该成功
        mockMvc.perform(post("/api/hello/aop-demo")
                        .param("demoId", demoId)
                        .param("processTime", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        // 立即发送相同请求，应该被幂等性拦截
        mockMvc.perform(post("/api/hello/aop-demo")
                        .param("demoId", demoId)
                        .param("processTime", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(40500)) // OPERATION_ERROR
                .andExpect(jsonPath("$.message").value("演示请求重复提交，请等待5秒后重试"));
    }
}