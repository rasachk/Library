package com.rasachk.libraryapi.book.dto;


public class BookDto {
    private String title;
    private String author;
    private String language;
    private Integer pageNumbers;
    private String description;
    private Boolean borrowStatus;

    public BookDto() {
    }

    public BookDto(String title, String author, String language, Integer pageNumbers, String description, Boolean borrowStatus) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.pageNumbers = pageNumbers;
        this.description = description;
        this.borrowStatus = borrowStatus;
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
}
