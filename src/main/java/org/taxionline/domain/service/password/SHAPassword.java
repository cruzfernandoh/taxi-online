package org.taxionline.domain.service.password;

import org.apache.commons.codec.digest.DigestUtils;
import org.taxionline.domain.entity.vo.Password;

public class SHAPassword extends Password {

    public SHAPassword(String value) {
        super(value);
    }

    @Override
    protected String encrypt(String password) {
        return DigestUtils.sha1Hex(password);
    }

    @Override
    protected boolean isValid(String value) {
        return this.value.equals(this.encrypt(value));
    }
}
