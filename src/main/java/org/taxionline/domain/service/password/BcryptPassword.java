package org.taxionline.domain.service.password;

import org.mindrot.jbcrypt.BCrypt;
import org.taxionline.domain.entity.vo.Password;

public class BcryptPassword extends Password {

    public BcryptPassword(String value) {
        super(value);
    }

    @Override
    protected String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    protected boolean isValid(String value) {
        return this.value.equals(this.encrypt(value));
    }
}
