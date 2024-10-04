package com.spacecraft.domain.spacecraft.converter;

import com.spacecraft.domain.spacecraft.Name;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)  // autoApply = true to automatically add the converter
public class NameConverter implements AttributeConverter<Name, String> {

    // Converts the object Name to a String to save it on database
    @Override
    public String convertToDatabaseColumn(Name name) {
        return name != null ? name.value() : null;
    }

    // Converts the String from the database to a Name Object
    @Override
    public Name convertToEntityAttribute(String dbData) {
        return dbData != null ? new Name(dbData) : null;
    }
}
