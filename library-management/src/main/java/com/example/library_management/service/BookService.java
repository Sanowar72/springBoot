package com.example.library_management.service;

import com.example.library_management.dto.BookPageResponse;
import com.example.library_management.dto.PagedResponse;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.model.Book;
import com.example.library_management.model.BookCategory;
import com.example.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.library_management.dto.PageMeta;
import org.springframework.data.domain.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // ➤ ADD BOOK
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // ➤ GET ALL BOOKS
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // ➤ GET BOOK BY ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with ID: " + id));
    }

    // ➤ UPDATE BOOK
    public Book updateBook(Long id, Book updatedBook) {

        Book existing = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with ID: " + id));

        // Set updated fields
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setCategory(updatedBook.getCategory());
        existing.setPrice(updatedBook.getPrice());
        existing.setStatus(updatedBook.getStatus());

        return bookRepository.save(existing);
    }

    // ➤ DELETE BOOK
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    public List<Book> addMultipleBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    /**
     * Search books with pagination + sorting.
     * - If both title and author provided -> search by both (AND)
     * - Else if title provided -> search by title
     * - Else if author provided -> search by author
     * - Else if category provided -> search by category
     * - Else -> return all paged
     */
    public PagedResponse<Book> searchBooks(
            String title,
            String author,
            BookCategory category,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort.Direction dir = Sort.Direction.fromString(sortDir == null ? "ASC" : sortDir);
        Sort sort = Sort.by(dir, (sortBy == null || sortBy.isBlank()) ? "id" : sortBy);
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), sort);

        Page<Book> pg;

        boolean hasTitle = title != null && !title.isBlank();
        boolean hasAuthor = author != null && !author.isBlank();

        if (hasTitle && hasAuthor) {
            pg = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title.trim(), author.trim(), pageable);
        } else if (hasTitle) {
            pg = bookRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);
        } else if (hasAuthor) {
            pg = bookRepository.findByAuthorContainingIgnoreCase(author.trim(), pageable);
        } else if (category != null) {
            pg = bookRepository.findByCategory(category, pageable);
        } else {
            pg = bookRepository.findAll(pageable);
        }

        List<Book> items = pg.getContent().stream().collect(Collectors.toList());
        PageMeta meta = new PageMeta(pg.getNumber(), pg.getSize(), pg.getTotalElements(), pg.getTotalPages());
        return new PagedResponse<>(items, meta);
    }


    public List<Book> findBooks(String title,
                                String author,
                                BookCategory category,
                                int page,
                                int size,
                                String sortBy,
                                String sortDir) {

        Sort.Direction dir = Sort.Direction.fromString(sortDir == null ? "ASC" : sortDir);
        Sort sort = Sort.by(dir, (sortBy == null || sortBy.isBlank()) ? "id" : sortBy);
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), sort);

        Page<Book> pg;

        boolean hasTitle = title != null && !title.isBlank();
        boolean hasAuthor = author != null && !author.isBlank();

        if (hasTitle && hasAuthor) {
            pg = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(
                    title.trim(), author.trim(), pageable);
        } else if (hasTitle) {
            pg = bookRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);
        } else if (hasAuthor) {
            pg = bookRepository.findByAuthorContainingIgnoreCase(author.trim(), pageable);
        } else if (category != null) {
            pg = bookRepository.findByCategory(category, pageable);
        } else {
            pg = bookRepository.findAll(pageable);
        }

        // return the page content as a simple List
        return pg.getContent();
    }
    public BookPageResponse searchBooksPage(
            String title,
            String author,
            BookCategory category,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort.Direction dir = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        Page<Book> pg;

        boolean hasTitle = title != null && !title.isBlank();
        boolean hasAuthor = author != null && !author.isBlank();

        if (hasTitle && hasAuthor) {
            pg = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author, pageable);
        } else if (hasTitle) {
            pg = bookRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (hasAuthor) {
            pg = bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
        } else if (category != null) {
            pg = bookRepository.findByCategory(category, pageable);
        } else {
            pg = bookRepository.findAll(pageable);
        }

        return new BookPageResponse(
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages(),
                pg.isFirst(),
                pg.isLast(),
                pg.getContent()
        );
    }


}
