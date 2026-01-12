package org.example.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nanak
 *
 * 方法执行时间监控注解
 * 
 * 使用示例：
 * @TimeConsuming(threshold = 1000)
 * public Result<List<User>> queryUsers() {
 *     // 业务逻辑
 * }
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeConsuming {
    /**
     * 执行时间阈值，单位毫秒
     * 超过此阈值将输出警告日志
     */
    long threshold() default 500;
}
