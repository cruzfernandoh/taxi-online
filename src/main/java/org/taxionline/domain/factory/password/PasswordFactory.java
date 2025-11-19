package org.taxionline.domain.factory.password;

import org.taxionline.domain.entity.vo.Password;
import org.taxionline.domain.service.password.BcryptPassword;
import org.taxionline.domain.service.password.MD5Password;
import org.taxionline.domain.service.password.SHAPassword;
import org.taxionline.domain.service.password.TextPlainPassword;

public class PasswordFactory {

    public static Password create(String value, String passwordType) {
        return switch (passwordType) {
            case "textplain" -> new TextPlainPassword(value);
            case "md5" -> new MD5Password(value);
            case "sha1" -> new SHAPassword(value);
            case "bcrypt" -> new BcryptPassword(value);
            default -> throw new IllegalStateException("Unexpected value: " + passwordType);
        };
    }
}
