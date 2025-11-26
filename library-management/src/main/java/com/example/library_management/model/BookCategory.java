package com.example.library_management.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BookCategory {
    FICTION,
    NON_FICTION,
    EDUCATION,
    SCIENCE,
    TECHNOLOGY,
    HISTORY,
    BIOGRAPHY;

    @JsonCreator
    public static BookCategory fromString(String value) {
        try {
            return BookCategory.valueOf(value.trim().toUpperCase());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid BookCategory value: " + value);
        }
    }
}
