package com.rasachk.libraryapi.issue.entity;

import com.rasachk.libraryapi.book.entity.Book;
import com.rasachk.libraryapi.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "LIB_ISSUES")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ISSUE_ID")
    private Long id;
    @Column(name = "ISSUE_START_DATE")
    private LocalDate startDate;
    @Column(name = "ISSUE_RETURN_DATE")
    private LocalDate returnDate;
    @Column(name = "ISSUE_PERIOD")
    private Integer period;
    @JoinColumn(name = "MEMBER", referencedColumnName = "MEMBER_USERNAME")
    @OneToOne(cascade = CascadeType.ALL)
    private Member member;
    @JoinColumn(name = "BOOK", referencedColumnName = "BOOK_TITLE")
    @OneToOne(cascade = CascadeType.ALL)
    private Book book;

    public Issue() {
    }

    public Issue(LocalDate startDate, Integer period, Member member, Book book) {
        this.startDate = startDate;
        this.period = period;
        this.member = member;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return String.format("Issue [id=%s, startDate=%s, returnDate=%s, period=%s,member=%s book=%s]", id, startDate, returnDate,period,member,book);
    }
}
