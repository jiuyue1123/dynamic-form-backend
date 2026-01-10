package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TraceIdInterceptor 单元测试
 */
@SpringBootTest
class TraceIdInterceptorTest {

    private TraceIdInterceptor traceIdInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        traceIdInterceptor = new TraceIdInterceptor();
        // 确保每个测试开始前MDC是干净的
        MDC.clear();
    }

    @AfterEach
    void tearDown() {
        // 确保每个测试结束后MDC是干净的
        MDC.clear();
    }

    @Test
    void testPreHandleGeneratesTraceIdWhenNotProvided() throws Exception {
        // 准备测试数据 - 请求头中没有traceId
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn(null);

        // 执行测试
        boolean result = traceIdInterceptor.preHandle(request, response, handler);

        // 验证结果
        assertTrue(result);

        // 验证MDC中设置了traceId
        String traceId = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertNotNull(traceId);
        assertFalse(traceId.isEmpty());
        assertEquals(32, traceId.length()); // UUID去掉横线后的长度

        // 验证响应头设置了traceId
        verify(response).setHeader(TraceIdInterceptor.TRACE_ID_KEY, traceId);
    }

    @Test
    void testPreHandleUsesProvidedTraceId() throws Exception {
        // 准备测试数据 - 请求头中提供了traceId
        String providedTraceId = "custom-trace-id-12345";
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn(providedTraceId);

        // 执行测试
        boolean result = traceIdInterceptor.preHandle(request, response, handler);

        // 验证结果
        assertTrue(result);

        // 验证MDC中使用了提供的traceId
        String traceId = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertEquals(providedTraceId, traceId);

        // 验证响应头设置了相同的traceId
        verify(response).setHeader(TraceIdInterceptor.TRACE_ID_KEY, providedTraceId);
    }

    @Test
    void testPreHandleWithEmptyTraceIdGeneratesNew() throws Exception {
        // 准备测试数据 - 请求头中的traceId为空字符串
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn("");

        // 执行测试
        boolean result = traceIdInterceptor.preHandle(request, response, handler);

        // 验证结果
        assertTrue(result);

        // 验证MDC中生成了新的traceId
        String traceId = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertNotNull(traceId);
        assertFalse(traceId.isEmpty());
        assertEquals(32, traceId.length());

        // 验证响应头设置了生成的traceId
        verify(response).setHeader(TraceIdInterceptor.TRACE_ID_KEY, traceId);
    }

    @Test
    void testPreHandleWithWhitespaceTraceIdGeneratesNew() throws Exception {
        // 准备测试数据 - 请求头中的traceId为空白字符
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn("   ");

        // 执行测试
        boolean result = traceIdInterceptor.preHandle(request, response, handler);

        // 验证结果
        assertTrue(result);

        // 验证MDC中生成了新的traceId
        String traceId = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertNotNull(traceId);
        assertFalse(traceId.isEmpty());
        assertEquals(32, traceId.length());

        // 验证响应头设置了生成的traceId
        verify(response).setHeader(TraceIdInterceptor.TRACE_ID_KEY, traceId);
    }

    @Test
    void testAfterCompletionClearsMDC() throws Exception {
        // 准备测试数据 - 先设置MDC
        MDC.put(TraceIdInterceptor.TRACE_ID_KEY, "test-trace-id");
        MDC.put("other-key", "other-value");

        // 验证MDC中有数据
        assertNotNull(MDC.get(TraceIdInterceptor.TRACE_ID_KEY));
        assertNotNull(MDC.get("other-key"));

        // 执行测试
        traceIdInterceptor.afterCompletion(request, response, handler, null);

        // 验证MDC被清空
        assertNull(MDC.get(TraceIdInterceptor.TRACE_ID_KEY));
        assertNull(MDC.get("other-key"));
    }

    @Test
    void testAfterCompletionWithException() throws Exception {
        // 准备测试数据 - 先设置MDC
        MDC.put(TraceIdInterceptor.TRACE_ID_KEY, "test-trace-id");

        // 模拟异常情况
        Exception testException = new RuntimeException("Test exception");

        // 执行测试
        traceIdInterceptor.afterCompletion(request, response, handler, testException);

        // 验证即使有异常，MDC也被清空
        assertNull(MDC.get(TraceIdInterceptor.TRACE_ID_KEY));
    }

    @Test
    void testCompleteRequestLifecycle() throws Exception {
        // 测试完整的请求生命周期

        // 1. 请求开始 - preHandle
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn(null);
        boolean preHandleResult = traceIdInterceptor.preHandle(request, response, handler);

        assertTrue(preHandleResult);
        String traceId = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertNotNull(traceId);

        // 2. 模拟业务处理过程中MDC仍然可用
        assertEquals(traceId, MDC.get(TraceIdInterceptor.TRACE_ID_KEY));

        // 3. 请求结束 - afterCompletion
        traceIdInterceptor.afterCompletion(request, response, handler, null);

        // 4. 验证MDC被清空
        assertNull(MDC.get(TraceIdInterceptor.TRACE_ID_KEY));
    }

    @Test
    void testMultipleRequestsIsolation() throws Exception {
        // 测试多个请求之间的隔离性

        // 第一个请求
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn("trace-id-1");
        traceIdInterceptor.preHandle(request, response, handler);
        String traceId1 = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertEquals("trace-id-1", traceId1);

        // 模拟第一个请求结束
        traceIdInterceptor.afterCompletion(request, response, handler, null);
        assertNull(MDC.get(TraceIdInterceptor.TRACE_ID_KEY));

        // 第二个请求
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn("trace-id-2");
        traceIdInterceptor.preHandle(request, response, handler);
        String traceId2 = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertEquals("trace-id-2", traceId2);

        // 验证两个traceId不同
        assertNotEquals(traceId1, traceId2);

        // 清理
        traceIdInterceptor.afterCompletion(request, response, handler, null);
    }

    @Test
    void testTraceIdConstant() {
        // 验证常量值
        assertEquals("traceId", TraceIdInterceptor.TRACE_ID_KEY);
    }

    @Test
    void testGeneratedTraceIdFormat() throws Exception {
        // 测试生成的traceId格式
        when(request.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn(null);

        traceIdInterceptor.preHandle(request, response, handler);

        String traceId = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertNotNull(traceId);

        // 验证长度（UUID去掉横线后应该是32位）
        assertEquals(32, traceId.length());

        // 验证只包含字母和数字（UUID的十六进制格式）
        assertTrue(traceId.matches("[a-f0-9]{32}"));

        // 验证不包含横线
        assertFalse(traceId.contains("-"));
    }

    @Test
    void testConcurrentRequests() throws Exception {
        // 测试并发请求的MDC隔离
        // 注意：这个测试主要验证逻辑，真正的线程隔离由MDC的ThreadLocal机制保证

        // 模拟两个不同的请求
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        HttpServletRequest request2 = mock(HttpServletRequest.class);
        HttpServletResponse response1 = mock(HttpServletResponse.class);
        HttpServletResponse response2 = mock(HttpServletResponse.class);

        when(request1.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn("concurrent-trace-1");
        when(request2.getHeader(TraceIdInterceptor.TRACE_ID_KEY)).thenReturn("concurrent-trace-2");

        // 在同一线程中模拟处理不同请求（实际应用中会在不同线程）
        traceIdInterceptor.preHandle(request1, response1, handler);
        String traceId1 = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertEquals("concurrent-trace-1", traceId1);

        // 清理第一个请求
        traceIdInterceptor.afterCompletion(request1, response1, handler, null);

        // 处理第二个请求
        traceIdInterceptor.preHandle(request2, response2, handler);
        String traceId2 = MDC.get(TraceIdInterceptor.TRACE_ID_KEY);
        assertEquals("concurrent-trace-2", traceId2);

        // 清理第二个请求
        traceIdInterceptor.afterCompletion(request2, response2, handler, null);
    }
}