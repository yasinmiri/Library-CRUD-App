package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.repo.BookRepository;
import com.example.demo.service.BookService;
import jakarta.persistence.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/health")
    public String health(){
        return bookService.health();
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/getBookByAuthor/{author}")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable String author) {
        return bookService.getBookByAuthor(author);
    }

    @GetMapping("/getBookByTitle/{title}")
    public ResponseEntity<List<Book>> getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/getBooksBeforeYear/{publicationYear}")
    public ResponseEntity<List<Book>> getBooksBeforeYear(@PathVariable long publicationYear) {
        return bookService.getBooksBeforeYear(publicationYear);
    }

    @GetMapping("/getBooksByPageNum/{pageNum}")
    public ResponseEntity<List<Book>> BooksByPageNum(@PathVariable long pageNum) {
        return bookService.BooksByPageNum(pageNum);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PostMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
    @DeleteMapping("/deleteAllBooks")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        return bookService.deleteAllBooks();
    }
}