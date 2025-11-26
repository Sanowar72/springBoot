package com.example.library_management.dto;

import com.example.library_management.model.BookCategory;
import com.example.library_management.model.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Book title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @NotNull(message = "Category is required")
    private BookCategory category;

    @NotNull(message = "Status is required")
    private BookStatus status;
}
