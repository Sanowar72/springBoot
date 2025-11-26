package com.example.library_management.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookStatus {

    AVAILABLE,
    BORROWED,
    RESERVED,
    LOST;

    // --- Make ENUM case-insensitive ---
    @JsonCreator
    public static BookStatus fromString(String value) {
        return BookStatus.valueOf(value.toUpperCase());
    }

    // --- Optional: return value in lowercase for cleaner JSON ---
    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }
}
