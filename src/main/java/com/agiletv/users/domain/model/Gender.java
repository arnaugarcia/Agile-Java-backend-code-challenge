package com.agiletv.users.domain.model;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Stream;

public enum Gender {
    MALE, FEMALE, OTHER;

    private static final Map<String, Gender> GENDER_MAP = Stream.of(values())
        .collect(toMap(Enum::name, gender -> gender));

    public static Gender fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        Gender gender = GENDER_MAP.get(value.toUpperCase());
        if (gender == null) {
            throw new IllegalArgumentException("Invalid gender: " + value);
        }
        return gender;
    }
}
