package org.example.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.constraint.MobileValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nanak
 *
 * 身份证号校验注解
 */
// 指定校验器
@Constraint(validatedBy = MobileValidator.class)
// 注解目标：字段、方法参数
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCard {
    /**
     * 校验失败提示信息
     */
    String message() default "must be a well-formed ID number";

    /**
     * 分组校验（可自定义分组，如新增/修改）
     */
    Class<?>[] groups() default {};

    /**
     * 负载（自定义附加信息，一般用不到）
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 可选参数：是否允许为空（默认true，即不能为空）
     */
    boolean required() default true;
}
