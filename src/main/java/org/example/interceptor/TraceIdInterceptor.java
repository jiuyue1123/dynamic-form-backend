package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * @author nanak
 *
 * MDC 链路追踪拦截器：生成 traceId 并放入 MDC
 */
@Component
public class TraceIdInterceptor implements HandlerInterceptor {

    // 定义 traceId 在 MDC 中的 key（统一命名，方便日志配置）
    public static final String TRACE_ID_KEY = "traceId";

    /**
     * 请求处理前：生成 traceId 并放入 MDC
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 优先从请求头获取 traceId（跨服务调用时传递），没有则生成
        String traceId = request.getHeader(TRACE_ID_KEY);
        if (traceId == null || traceId.trim().isEmpty()) {
            // 生成 UUID 并去掉横线，缩短长度（也可直接用 UUID.randomUUID().toString()）
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        // 2. 放入 MDC（MDC 底层是 ThreadLocal，仅当前线程可见）
        MDC.put(TRACE_ID_KEY, traceId);
        // 3. 可选：将 traceId 放入响应头，方便前端查看
        response.setHeader(TRACE_ID_KEY, traceId);
        return true;
    }

    /**
     * 请求处理后：清理 MDC（必须！否则线程池复用会导致 traceId 串号）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空当前线程的 MDC 数据
        MDC.clear();
    }
}