package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Entity
@Table(name="Authors")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private long birthYear;

}