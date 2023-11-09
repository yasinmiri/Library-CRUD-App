package com.example.demo.repo;

import com.example.demo.model.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //@Query("SELECT b FROM Book b WHERE b.author = :author")
    //List<Book> findByAuthor(String author);
    List<Book> findByAuthor(String author);

    //@Query("SELECT b FROM Book b WHERE b.title = :title")
    //List<Book> findByTitle(String title);
    List<Book> findByTitle(String author);

    @Query("SELECT b FROM Book b WHERE b.publicationYear < :publicationYear")
    List<Book> findBeforeYear(long publicationYear);

    @Query("SELECT b FROM Book b WHERE b.pageNum BETWEEN :pageNum - 50 AND :pageNum + 50")
    List<Book> findAroundPageNum(@Param("pageNum") long pageNum);
}



