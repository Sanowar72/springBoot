package com.example.library_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Book title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    private BookCategory category;
}
