package org.taxionline.domain.entity.vo.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.taxionline.domain.entity.vo.CPF;

@Converter(autoApply = true)
public class CPFConverter implements AttributeConverter<CPF, String> {

    @Override
    public String convertToDatabaseColumn(CPF cpf) {
        return cpf == null ? null : cpf.getValue();
    }

    @Override
    public CPF convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : new CPF(dbValue);
    }
}
