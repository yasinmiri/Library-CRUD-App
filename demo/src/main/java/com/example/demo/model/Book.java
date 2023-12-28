package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="Books")
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private long pageNum;

    @Column
    private long publicationYear;

    public Book() {
    }

    public Book(String title, String author, long pageNum, long publicationYear) {
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.publicationYear = publicationYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public void setPublicationYear(long publicationYear) {
        this.publicationYear = publicationYear;
    }
}