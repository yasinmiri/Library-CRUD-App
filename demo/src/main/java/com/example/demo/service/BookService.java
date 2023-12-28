package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repo.BookRepository;
import jakarta.persistence.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service

@Configuration

public class BookService {

    @Autowired
    BookRepository bookRepository;

    public String health(){
        log.debug("health endpoint is called.");
        return "Demo application is up.";
    }

    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> bookList = new ArrayList<>();
            bookRepository.findAll().forEach(bookList::add);

            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            log.debug("All books are listed.");
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookObj = bookRepository.findById(id);
        if (bookObj.isPresent()) {
            return new ResponseEntity<>(bookObj.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable String author) {
        try {
            List<Book> bookListByAuthor = bookRepository.findByAuthor(author);
            if (bookListByAuthor.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(bookListByAuthor, HttpStatus.OK);
            }
        } catch (Exception e){
            log.error("error occured in repository: " + e);
            throw e;
        }
    }

    public ResponseEntity<List<Book>> getBookByTitle(@PathVariable String title) {
        try {
            List<Book> bookListByTitle = bookRepository.findByTitle(title);
            if (bookListByTitle.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(bookListByTitle, HttpStatus.OK);
            }
        } catch (Exception e){
            log.error("error occured in repository: " + e);
            throw e;
        }
    }

    public ResponseEntity<List<Book>> getBooksBeforeYear(@PathVariable long publicationYear) {
        try {
            List<Book> bookListBeforeYear = bookRepository.findBeforeYear(publicationYear);
            if (bookListBeforeYear.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(bookListBeforeYear, HttpStatus.OK);
            }
        } catch (Exception e){
            log.error("error occured in repository: " + e);
            throw e;
        }
    }

    public ResponseEntity<List<Book>> BooksByPageNum(@PathVariable long pageNum) {
        try {
            List<Book> bookListAroundPageNum = bookRepository.findAroundPageNum(pageNum);
            if (bookListAroundPageNum.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(bookListAroundPageNum, HttpStatus.OK);
            }
        } catch (Exception e){
            log.error("error occured in repository: " + e);
            throw e;
        }
    }

    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book bookObj = bookRepository.save(book);
            log.debug("The book added to the library.");
            return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            Optional<Book> bookData = bookRepository.findById(id);
            if (bookData.isPresent()) {
                Book updatedBookData = bookData.get();
                updatedBookData.setTitle(book.getTitle());
                updatedBookData.setAuthor(book.getAuthor());

                Book bookObj = bookRepository.save(updatedBookData);
                return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {

        try {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            bookRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}