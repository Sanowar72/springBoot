package com.example.library_management.controller;

import com.example.library_management.dto.ApiResponse;
import com.example.library_management.model.Book;
import com.example.library_management.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // ➤ ADD A BOOK
    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody Book book) {
        Book saved = bookService.addBook(book);

        ApiResponse<Book> response = new ApiResponse<>(
                201,
                "Book added successfully",
                saved
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➤ GET ALL BOOKS
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();

        ApiResponse<List<Book>> response = new ApiResponse<>(
                200,
                "All books fetched successfully",
                books
        );

        return ResponseEntity.ok(response);
    }

    // ➤ GET BOOK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);

        ApiResponse<Book> resp = new ApiResponse<>(
                200,
                "Book fetched successfully",
                book
        );

        return ResponseEntity.ok(resp);
    }

    // ➤ UPDATE BOOK
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book updatedBook
    ) {
        Book updated = bookService.updateBook(id, updatedBook);

        ApiResponse<Book> response = new ApiResponse<>(
                200,
                "Book updated successfully",
                updated
        );

        return ResponseEntity.ok(response);
    }

    // ➤ DELETE BOOK
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        ApiResponse<String> response = new ApiResponse<>(
                200,
                "Book deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
