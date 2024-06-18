package com.rasachk.libraryapi.book.controller;

import com.rasachk.libraryapi.book.dto.BookDto;
import com.rasachk.libraryapi.book.service.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api
public class BookController {

    @Autowired
    private BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return new ResponseEntity<List<BookDto>>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping("books/{title}")
    public ResponseEntity<BookDto> findOneBook(@PathVariable String title) {
        return new ResponseEntity<BookDto>(bookService.convertBookToDto(bookService.findBook(title)), HttpStatus.OK);
    }

    @GetMapping("books/not-borrowed")
    public ResponseEntity<List<BookDto>> findNotBorrowedBooks() {
        return new ResponseEntity<List<BookDto>>(bookService.findNotBorrowedBooks(), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<Object> createBook(@RequestBody BookDto bookDto) {
        BookDto savedBookDto = bookService.saveBook(bookDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{title}").buildAndExpand(savedBookDto.getTitle()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("books/update")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<BookDto>(bookService.updateBook(bookDto), HttpStatus.OK);
    }

    @DeleteMapping("books/{title}")
    public ResponseEntity<String> deleteBook(@PathVariable String title) {
        bookService.deleteBook(title);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
