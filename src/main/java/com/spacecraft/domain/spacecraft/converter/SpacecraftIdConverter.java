package com.spacecraft.domain.spacecraft.converter;

import com.spacecraft.domain.spacecraft.SpacecraftId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)  // autoApply = true to automatically add the converter
public class SpacecraftIdConverter implements AttributeConverter<SpacecraftId, String> {

    // Converts the object SpacecraftId to a String to save it on database
    @Override
    public String convertToDatabaseColumn(SpacecraftId SpacecraftId) {
        return SpacecraftId != null ? SpacecraftId.id() : null;
    }

    // Converts the String from the database to a SpacecraftId Object
    @Override
    public SpacecraftId convertToEntityAttribute(String dbData) {
        return dbData != null ? new SpacecraftId(dbData) : null;
    }
}
