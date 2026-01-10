package org.example;

import jakarta.validation.constraints.Email;

/**
 * @author nanak
 */
public class TestDTO {
    @Email
    private String name;
}
