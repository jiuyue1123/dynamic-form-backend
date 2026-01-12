package org.example.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.aop.annotation.Idempotent;
import org.example.exception.BusinessException;
import org.example.enums.ErrorCode;
import org.example.utils.ToolKit;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nanak
 * 
 * 幂等切面 - 防止重复提交
 * 基于内存缓存实现，生产环境建议使用 Redis
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {

    /**
     * 内存缓存，存储请求标识和过期时间
     * 生产环境建议使用 Redis 替代
     */
    private final ConcurrentHashMap<String, Long> requestCache = new ConcurrentHashMap<>();

    /**
     * 默认幂等时间窗口（毫秒）
     */
    private static final long DEFAULT_EXPIRE_TIME = 5000L;

    @Around("@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        // 生成请求唯一标识
        String requestKey = generateRequestKey(joinPoint, idempotent);
        
        log.debug("Idempotent check started, request key: {}", requestKey);
        
        // 检查是否重复请求
        if (isDuplicateRequest(requestKey)) {
            log.warn("Duplicate request detected, request key: {}", requestKey);
            throw new BusinessException(ErrorCode.OPERATION_ERROR.getCode(), idempotent.message());
        }
        
        try {
            // 执行原方法
            Object result = joinPoint.proceed();
            log.debug("Idempotent check passed, method executed successfully, request key: {}", requestKey);
            return result;
        } finally {
            // 清理过期的缓存项
            cleanExpiredCache();
        }
    }

    /**
     * 生成请求唯一标识
     */
    private String generateRequestKey(ProceedingJoinPoint joinPoint, Idempotent idempotent) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 基础标识：类名 + 方法名
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(method.getDeclaringClass().getSimpleName())
                  .append(".")
                  .append(method.getName());
        
        // 如果指定了参数名，则根据参数值生成标识
        String paramName = idempotent.value();
        if (!"key".equals(paramName)) {
            Object[] args = joinPoint.getArgs();
            String[] paramNames = signature.getParameterNames();
            
            // 查找指定参数的值
            for (int i = 0; i < paramNames.length; i++) {
                if (paramName.equals(paramNames[i]) && args[i] != null) {
                    keyBuilder.append(":").append(args[i].toString());
                    break;
                }
            }
        } else {
            // 默认使用所有参数的哈希值
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                String argsJson = ToolKit.JSON.toJsonString(args);
                String argsHash = ToolKit.CRYPTO.md5(argsJson);
                keyBuilder.append(":").append(argsHash);
            }
        }
        
        return keyBuilder.toString();
    }

    /**
     * 检查是否为重复请求
     */
    private boolean isDuplicateRequest(String requestKey) {
        long currentTime = System.currentTimeMillis();
        Long existTime = requestCache.get(requestKey);
        
        if (existTime != null && currentTime < existTime) {
            // 存在且未过期，为重复请求
            return true;
        }
        
        // 记录新请求，设置过期时间
        long expireTime = currentTime + DEFAULT_EXPIRE_TIME;
        requestCache.put(requestKey, expireTime);
        
        return false;
    }

    /**
     * 清理过期的缓存项
     */
    private void cleanExpiredCache() {
        long currentTime = System.currentTimeMillis();
        requestCache.entrySet().removeIf(entry -> currentTime >= entry.getValue());
    }
}
