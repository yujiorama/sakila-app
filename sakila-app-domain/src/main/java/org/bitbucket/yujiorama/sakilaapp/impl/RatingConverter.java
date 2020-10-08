package org.bitbucket.yujiorama.sakilaapp.impl;

import org.bitbucket.yujiorama.sakilaapp.model.RatingEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<RatingEnum, String> {
    @Override
    public String convertToDatabaseColumn(final RatingEnum attribute) {
        return attribute.getLabel();
    }

    @Override
    public RatingEnum convertToEntityAttribute(final String dbData) {
        return RatingEnum.of(dbData);
    }
}
