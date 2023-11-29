package com.jiring.libraryapi.book.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LIB_BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOK_ID")
    private Long id;
    @Column(name = "BOOK_TITLE")
    private String title;
    @Column(name = "BOOK_AUTHOR")
    private String author;
    @Column(name = "BOOK_LANGUAGE")
    private String language;
    @Column(name = "BOOK_PAGE_NUMBERS")
    private Integer pageNumbers;
    @Column(name = "BOOK_DESCRIPTION")
    private String description;
    @Column(name = "BOOK_BORROW_STATUS")
    private Boolean borrowStatus;
    @Column(name = "BOOK_AVAILABILITY")
    private Boolean availability = true;

    public Book() {
    }

    public Book(Long id, String title, String author, String language, Integer pageNumbers, String description, Boolean borrowStatus, Boolean availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.language = language;
        this.pageNumbers = pageNumbers;
        this.description = description;
        this.borrowStatus = borrowStatus;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(Integer pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Boolean borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return String.format("Book [id=%s, title=%s, author=%s, pageNumbers=%s,borrowStatus=%s availability=%s]", id, title, author, language, pageNumbers, borrowStatus, availability);
    }
}
