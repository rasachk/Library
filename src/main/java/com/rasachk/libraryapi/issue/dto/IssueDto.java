package com.rasachk.libraryapi.issue.dto;

import com.rasachk.libraryapi.book.dto.BookDto;
import com.rasachk.libraryapi.member.model.dto.MemberDto;

import java.time.LocalDate;

public class IssueDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate returnDate;
    private Integer period;
    private MemberDto member;
    private BookDto book;

    public IssueDto() {
    }

    public IssueDto(Long id, LocalDate startDate, LocalDate returnDate, Integer period, MemberDto memberDto, BookDto bookDto) {
        this.id = id;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.period = period;
        this.member = memberDto;
        this.book = bookDto;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public MemberDto getMember() {
        return member;
    }

    public void setMember(MemberDto memberDto) {
        this.member = memberDto;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto bookDto) {
        this.book = bookDto;
    }
}
