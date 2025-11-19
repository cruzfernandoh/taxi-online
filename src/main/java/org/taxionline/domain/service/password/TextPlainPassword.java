package org.taxionline.domain.service.password;

import org.taxionline.domain.entity.vo.Password;

public class TextPlainPassword extends Password {

    public TextPlainPassword(String value) {
        super(value);
    }

    @Override
    protected String encrypt(String password) {
        return password;
    }

    @Override
    protected boolean isValid(String value) {
        return this.value.equals(value);
    }
}
