package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repo.BookRepository;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeGroups;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @DataJpaTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void healthTest() {
        assertEquals("Demo application is up.", bookService.health());
    }

    @Test
    void getAllBooksTestWhenNoBooks() {

        // bookService.getAllBooks();
        // verify(bookRepository).findAll();
        assertNotNull(bookService.getAllBooks());
    }

    @Test
    void getAllBooksWhenBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setAuthor("Author1");
        book1.setId(1L);
        book1.setPageNum(1L);
        book1.setPublicationYear(1L);
        book1.setTitle("book1");

        Book book2 = new Book();
        book2.setAuthor("Author2");
        book2.setId(1L);
        book2.setPageNum(1L);
        book2.setPublicationYear(1L);
        book2.setTitle("book2");

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookRepository.findAll()).thenReturn(bookList);

        ResponseEntity<List<Book>> actualAllBooks = bookService.getAllBooks();

        assertEquals(200, actualAllBooks.getStatusCodeValue());
        assertEquals(bookList, actualAllBooks.getBody());
    }

    @Test
    void GetAllBooksException() {
        when(bookRepository.findAll()).thenThrow(new NoSuchElementException());

        ResponseEntity<List<Book>> actualResponse = bookService.getAllBooks();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

    @Test
    void GetBookByIdWhenBooks() {
        Book b = new Book();

        b.setId(1L);
        b.setAuthor("Author1");
        b.setPageNum(1L);
        b.setPublicationYear(2022L);
        b.setTitle("Book1");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(b));

        ResponseEntity<Book> actualResponse = bookService.getBookById(1L);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(b, actualResponse.getBody());
    }

    @Test
    void getBookByIdException() {
        when(bookRepository.findById(-99L)).thenReturn(Optional.empty());

        ResponseEntity<Book> actualResponse = bookService.getBookById(-99L);

        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    void GetBookByAuthorWhenNoAuthor() {
        when(bookRepository.findByAuthor(Mockito.any()))
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<Book>> actualResponse = bookService.getBookByAuthor("Author1");

        assertEquals(204, actualResponse.getStatusCodeValue());
    }

    @Test
    void GetBookByAuthorWhenAuthor() {

        Book book = new Book();
        book.setAuthor("Book3");
        book.setId(1L);
        book.setPageNum(1L);
        book.setPublicationYear(1L);
        book.setTitle("Author3");

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookRepository.findByAuthor("Author3")).thenReturn(bookList);

        ResponseEntity<List<Book>> actualBookByAuthor = bookService.getBookByAuthor("Author3");

        assertEquals(200, actualBookByAuthor.getStatusCodeValue());
        assertTrue(actualBookByAuthor.hasBody());
        assertTrue(actualBookByAuthor.getHeaders().isEmpty());
    }

    @Test
    void getBookByAuthorException() {
        when(bookRepository.findByAuthor("AuthorException")).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bookService.getBookByAuthor("AuthorException"));
    }

    @Test
    void getBookByTitleWhenEmpty() {
        when(bookRepository.findByTitle(Mockito.<String>any())).thenReturn(new ArrayList<>());

        ResponseEntity<List<Book>> actualBookByTitle = bookService.getBookByTitle("Author1");

        assertEquals(204, actualBookByTitle.getStatusCodeValue());
    }

    @Test
    void getBookByTitleWhenTitle() {
        Book book = new Book();
        book.setAuthor("Book1");
        book.setId(1L);
        book.setPageNum(1L);
        book.setPublicationYear(1L);
        book.setTitle("Author1");

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookRepository.findByTitle(Mockito.<String>any())).thenReturn(bookList);

        ResponseEntity<List<Book>> actualBookByTitle = bookService.getBookByTitle("Author1");

        assertEquals(200, actualBookByTitle.getStatusCodeValue());
    }

    @Test
    void getBookByTitleException() {
        when(bookRepository.findByTitle(anyString())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bookService.getBookByTitle("Title"));
    }

    @Test
    void getBooksBeforeYearWhenEmpty() {
        when(bookRepository.findBeforeYear(anyLong())).thenReturn(new ArrayList<>());

        ResponseEntity<List<Book>> actualBooksBeforeYear = bookService.getBooksBeforeYear(2000L);

        assertEquals(204, actualBooksBeforeYear.getStatusCodeValue());;
    }

    @Test
    void getBooksBeforeWhenYear() {

        Book book = new Book();
        book.setAuthor("Book1");
        book.setId(1L);
        book.setPageNum(1L);
        book.setPublicationYear(1L);
        book.setTitle("Author1");

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookRepository.findBeforeYear(anyLong())).thenReturn(bookList);

        ResponseEntity<List<Book>> actualBooksBeforeYear = bookService.getBooksBeforeYear(1L);

        assertEquals(200, actualBooksBeforeYear.getStatusCodeValue());
    }

    @Test
    void getBooksBeforeYearException() {
        when(bookRepository.findBeforeYear(2022L)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bookService.getBooksBeforeYear(2022L));
    }

    @Test
    void testBooksByPageNum() {
        when(bookRepository.findAroundPageNum(anyLong())).thenReturn(new ArrayList<>());

        ResponseEntity<List<Book>> actualBooksByPageNumResult = bookService.BooksByPageNum(1L);

        assertEquals(204, actualBooksByPageNumResult.getStatusCodeValue());
    }

    @Test
    void testBooksByPageNum2() {
        Book book = new Book();
        book.setAuthor("Book5");
        book.setId(1L);
        book.setPageNum(1L);
        book.setPublicationYear(1L);
        book.setTitle("Author5");

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookRepository.findAroundPageNum(anyLong())).thenReturn(bookList);

        ResponseEntity<List<Book>> actualBooksByPageNumResult = bookService.BooksByPageNum(1L);

        assertEquals(200, actualBooksByPageNumResult.getStatusCodeValue());
    }

    @Test
    void booksByPageNumException() {
        when(bookRepository.findAroundPageNum(100L)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bookService.BooksByPageNum(100L));
    }

    @Test
    void testAddBook() {

        Book book1 = new Book();
        book1.setAuthor("Book1");
        book1.setId(1L);
        book1.setPageNum(1L);
        book1.setPublicationYear(1L);
        book1.setTitle("Author1");
        when(bookRepository.save(Mockito.<Book>any())).thenReturn(book1);

        ResponseEntity<Book> actualResponse = bookService.addBook(book1);

        assertEquals(201, actualResponse.getStatusCodeValue());
        assertTrue(actualResponse.hasBody());
    }

    @Test
    void addBookException() {
        Book book1 = new Book();
        book1.setAuthor("Book1");
        book1.setId(1L);
        book1.setPageNum(1L);
        book1.setPublicationYear(1L);
        book1.setTitle("Author1");

        when(bookRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bookService.addBook(book1));
    }

    @Test
    void deleteBookWhenID() {
        Book book1 = new Book();
        book1.setAuthor("Book1");
        book1.setId(1L);
        book1.setPageNum(1L);
        book1.setPublicationYear(1L);
        book1.setTitle("Author1");

        doNothing().when(bookRepository).deleteById(Mockito.<Long>any());

        ResponseEntity<HttpStatus> actualResponse = bookService.deleteBook(1L);

        assertEquals(200, actualResponse.getStatusCodeValue());
    }

    @Test
    void testDeleteBookException() {
        doThrow(new RuntimeException()).when(bookRepository).deleteById(any());

        ResponseEntity<HttpStatus> responseEntity = bookService.deleteBook(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

    @Test
    void testDeleteAllBooks() {
        doNothing().when(bookRepository).deleteAll();

        ResponseEntity<HttpStatus> actualDeleteAllBooksResult = bookService.deleteAllBooks();

        assertEquals(204, actualDeleteAllBooksResult.getStatusCodeValue());
    }

    @Test
    void deleteAllBooksException() {
        doThrow(new RuntimeException()).when(bookRepository).deleteAll();

        ResponseEntity<HttpStatus> responseEntity = bookService.deleteAllBooks();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }
}



