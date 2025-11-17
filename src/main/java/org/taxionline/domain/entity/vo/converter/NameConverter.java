package org.taxionline.domain.entity.vo.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.taxionline.domain.entity.vo.Name;

@Converter(autoApply = true)
public class NameConverter implements AttributeConverter<Name, String> {

    @Override
    public String convertToDatabaseColumn(Name name) {
        return name == null ? null : name.getValue();
    }

    @Override
    public Name convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : new Name(dbValue);
    }
}
