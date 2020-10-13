package org.bitbucket.yujiorama.sakilaapp.impl;

import org.bitbucket.yujiorama.sakilaapp.model.Rating;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(final Rating attribute) {
        return attribute.getLabel();
    }

    @Override
    public Rating convertToEntityAttribute(final String dbData) {
        return Rating.of(dbData);
    }
}
