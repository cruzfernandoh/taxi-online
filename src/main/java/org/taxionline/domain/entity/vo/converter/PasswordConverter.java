package org.taxionline.domain.entity.vo.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.taxionline.domain.entity.vo.Password;
import org.taxionline.domain.service.password.TextPlainPassword;

@Converter(autoApply = true)
public class PasswordConverter implements AttributeConverter<Password, String> {

    @Override
    public String convertToDatabaseColumn(Password password) {
        return password == null ? null : password.getValue();
    }

    @Override
    public Password convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : new TextPlainPassword(dbValue);
    }
}
