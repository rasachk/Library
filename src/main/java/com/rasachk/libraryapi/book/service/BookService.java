package com.rasachk.libraryapi.book.service;

import com.rasachk.libraryapi.book.dao.BookRepository;
import com.rasachk.libraryapi.book.dto.BookDto;
import com.rasachk.libraryapi.book.entity.Book;
import com.rasachk.libraryapi.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Book convertDtoToBook(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    public BookDto convertBookToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> findAllBooks() {
        return bookRepository.findAllByAvailability(true)
                .stream()
                .map(Book -> modelMapper.map(Book, BookDto.class))
                .collect(Collectors.toList());
    }

    public Book findBook(String title) {
        Book book = bookRepository.findBookByTitleAndAvailability(title, true);
        if (book == null) {
            throw new ResourceNotFoundException("Book", "title", title);
        }
        return book;
    }

    public BookDto saveBook(BookDto bookDto) {
        bookRepository.save(convertDtoToBook(bookDto));
        return bookDto;
    }

    public BookDto updateBook(BookDto bookDto) {
        Book book = convertDtoToBook(bookDto);
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setLanguage(bookDto.getLanguage());
        book.setPageNumbers(bookDto.getPageNumbers());
        book.setDescription(bookDto.getDescription());
        bookRepository.save(book);
        return bookDto;
    }

    public void deleteBook(String title) {
        Book book = findBook(title);
        book.setAvailability(false);
        bookRepository.save(book);
    }

    public void borrowBook(BookDto bookDto) {
        if (!bookDto.getBorrowStatus()) {
            Book book = findBook(bookDto.getTitle());
            book.setBorrowStatus(bookDto.getBorrowStatus());
            bookRepository.save(book);
        }
    }

    public void giveBookBack(BookDto bookDto) {
        if (bookDto.getBorrowStatus()) {
            Book book = findBook(bookDto.getTitle());
            book.setBorrowStatus(bookDto.getBorrowStatus());
            bookRepository.save(book);
        }
    }

    public List<BookDto> findNotBorrowedBooks() {
        return bookRepository.findAllByAvailabilityAndBorrowStatus(true, false)
                .stream()
                .map(Book -> modelMapper.map(Book, BookDto.class))
                .collect(Collectors.toList());
    }

    public Integer findBooksCount() {
        return bookRepository.findAllByAvailability(true).size();
    }
}
