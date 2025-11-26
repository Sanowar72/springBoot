package com.example.library_management.service;

import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.model.Book;
import com.example.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
