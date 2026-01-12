package org.example.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.aop.annotation.TimeConsuming;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author nanak
 * 
 * 方法耗时监控切面
 * 记录方法执行时间，超过阈值时输出警告日志
 */
@Slf4j
@Aspect
@Component
public class TimeConsumingAspect {

    @Around("@annotation(timeConsuming)")
    public Object around(ProceedingJoinPoint joinPoint, TimeConsuming timeConsuming) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String fullMethodName = className + "." + methodName;
        
        // 获取当前 traceId（如果存在）
        String traceId = MDC.get("traceId");
        String logPrefix = traceId != null ? "[" + traceId + "] " : "";
        
        long startTime = System.currentTimeMillis();
        log.debug("{}Method execution started: {}", logPrefix, fullMethodName);
        
        try {
            // 执行原方法
            Object result = joinPoint.proceed();
            
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // 检查是否超过阈值
            if (executionTime > timeConsuming.threshold()) {
                log.warn("{}Method execution time exceeded threshold: {} - execution time: {}ms (threshold: {}ms)", 
                        logPrefix, fullMethodName, executionTime, timeConsuming.threshold());
            } else {
                log.info("{}Method execution completed: {} - execution time: {}ms", 
                        logPrefix, fullMethodName, executionTime);
            }
            
            return result;
            
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.error("{}Method execution failed: {} - execution time: {}ms, error: {}", 
                    logPrefix, fullMethodName, executionTime, e.getMessage(), e);
            
            throw e;
        }
    }
}
