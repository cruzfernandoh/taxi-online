package org.taxionline.core.domain.account;

import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class Email {

    private String value;

    public Email(String value) {
        if (value == null || value.trim().isBlank())
            throw new ValidationException("Field email mandatory");
        if (!isValid(value))
            throw new ValidationException("Invalid CPF");
        this.value = value;
    }

    private boolean isValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]+@" +
                    "[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                    "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
    );
}
