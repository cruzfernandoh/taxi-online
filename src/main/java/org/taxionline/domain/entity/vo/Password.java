package org.taxionline.domain.entity.vo;

import jakarta.validation.ValidationException;
import lombok.Getter;

@Getter
public abstract class Password {

    protected final String value;

    public Password(String value) {
        if (value == null || value.trim().isBlank())
            throw new ValidationException("Field password mandatory");
        this.value = this.encrypt(value);
    }

    protected abstract String encrypt(String password);

    protected abstract boolean isValid(String value);
}
