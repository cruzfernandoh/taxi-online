package org.taxionline.core.domain.account;

import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class CPF {

    private String value;

    public CPF(String value) {
        if (value == null || value.trim().isBlank())
            throw new ValidationException("Field CPF mandatory");
        if (!isValid(value))
            throw new ValidationException("Invalid CPF");
        this.value = value;
    }

    private boolean isValid(String cpf) {
        return CPF_PATTERN.matcher(cpf).matches();
    }

    private static final Pattern CPF_PATTERN = Pattern.compile(
            "^(?!000\\.?000\\.?000-?00)" +
                    "(?!111\\.?111\\.?111-?11)" +
                    "(?!222\\.?222\\.?222-?22)" +
                    "(?!333\\.?333\\.?333-?33)" +
                    "(?!444\\.?444\\.?444-?44)" +
                    "(?!555\\.?555\\.?555-?55)" +
                    "(?!666\\.?666\\.?666-?66)" +
                    "(?!777\\.?777\\.?777-?77)" +
                    "(?!888\\.?888\\.?888-?88)" +
                    "(?!999\\.?999\\.?999-?99)" +
                    "([0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9]{2})$"
    );
}
