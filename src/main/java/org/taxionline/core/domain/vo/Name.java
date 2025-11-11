package org.taxionline.core.domain.vo;

import jakarta.validation.ValidationException;
import lombok.Getter;

@Getter
public class Name {

    private final String value;

    public Name(String value) {
        if (value == null || value.trim().isBlank())
            throw new ValidationException("Field name mandatory");
        this.value = value;
    }
}
