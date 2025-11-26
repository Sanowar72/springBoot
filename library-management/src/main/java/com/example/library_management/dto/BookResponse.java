package com.example.library_management.dto;

import com.example.library_management.model.BookCategory;
import com.example.library_management.model.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private double price;
    private BookCategory category;
    private BookStatus status;
}
