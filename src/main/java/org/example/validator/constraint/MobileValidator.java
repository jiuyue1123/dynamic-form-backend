package org.example.validator.constraint;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.validator.annotation.Mobile;

/**
 * @author nanak
 *
 * 手机号校验器
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    /**
     * 手机号正则表达式（11位中国号码）
     */
    private static final String PATTERN = "^1[3-9]\\d{9}$";

    /**
     * 是否必填（从注解获取参数）
     */
    private boolean required;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!required && StringUtils.isBlank(s)) {
            return true;
        }

        if (StringUtils.isBlank(s)) {
            return false;
        }

        return s.matches(PATTERN);
    }

    @Override
    public void initialize(Mobile constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }
}
