package com.rasachk.libraryapi.book.dao;

import com.rasachk.libraryapi.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAvailability(Boolean availability);

    List<Book> findAllByAvailabilityAndBorrowStatus(Boolean availability, Boolean borrowStatus);

    Book findBookByTitleAndAvailability(String title, Boolean availability);

}
