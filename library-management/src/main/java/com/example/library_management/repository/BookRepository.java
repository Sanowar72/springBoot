package com.example.library_management.repository;

import com.example.library_management.model.Book;
import com.example.library_management.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

}
