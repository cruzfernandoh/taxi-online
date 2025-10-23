package org.taxionline.core.domain.account;

import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Name {

    private String value;

    public Name(String value) {
        if (value == null || value.trim().isBlank())
            throw new ValidationException("Field name mandatory");
        this.value = value;
    }
}
