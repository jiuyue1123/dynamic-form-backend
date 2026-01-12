package org.example.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nanak
 *
 * 幂等注解 - 防止重复提交
 * 
 * 使用示例：
 * @Idempotent(value = "userId", message = "请勿重复提交")
 * public Result<String> createOrder(Long userId, OrderDTO order) {
 *     // 业务逻辑
 * }
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 唯一标识的参数名，默认使用所有参数的哈希值
     */
    String value() default "key";
    
    /**
     * 重复请求时的提示消息
     */
    String message() default "too many requests, please try again later";
}
