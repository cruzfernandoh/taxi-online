package org.taxionline.core.domain.vo.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.taxionline.core.domain.vo.Email;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email email) {
        return email == null ? null : email.getValue();
    }

    @Override
    public Email convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : new Email(dbValue);
    }
}
