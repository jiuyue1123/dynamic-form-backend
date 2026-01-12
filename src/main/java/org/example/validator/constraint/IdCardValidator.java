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
public class IdCardValidator implements ConstraintValidator<Mobile, String> {
    /**
     * 手机号正则表达式（11位中国号码）
     */
    private static final String PATTERN = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

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
