package com.example.library_management.controller;

import com.example.library_management.dto.ApiResponse;
import com.example.library_management.dto.PagedResponse;
import com.example.library_management.model.Book;
import com.example.library_management.model.BookCategory;
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

    // ➤ ADD MULTIPLE BOOKS
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<Book>>> addMultipleBooks(
            @Valid @RequestBody List<Book> books
    ) {
        List<Book> savedBooks = bookService.addMultipleBooks(books);

        ApiResponse<List<Book>> response = new ApiResponse<>(
                201,
                "Books added successfully",
                savedBooks
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ➤ GET ALL BOOKS (unchanged data but mapped to /books/all)
    @GetMapping("/all")
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
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        ApiResponse<Void> response = new ApiResponse<>(
                200,
                "Book deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    /**
     * GET /books
     * optional query params:
     *  - title (search by title, case-insensitive, contains)
     *  - author (search by author, case-insensitive, contains)
     *  - category (enum, case-insensitive)
     *  - page (default 0)
     *  - size (default 10)
     *  - sortBy (default "id")
     *  - sortDir (ASC or DESC, default ASC)
     */
    @GetMapping
    public ResponseEntity<PagedResponse<Book>> getBooksPaged(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) BookCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        PagedResponse<Book> resp = bookService.searchBooks(title, author, category, page, size, sortBy, sortDir);
        return ResponseEntity.ok(resp);
    }

    // Returns only the page content as a simple list (same logic as findBooks)
    @GetMapping("/list")
    public ResponseEntity<List<Book>> getBooksList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) BookCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        List<Book> books = bookService.findBooks(title, author, category, page, size, sortBy, sortDir);
        return ResponseEntity.ok(books);
    }

}
