package com.jiring.libraryapi.issue.controller;

import com.jiring.libraryapi.book.dto.BookDto;
import com.jiring.libraryapi.issue.dto.DateCount;
import com.jiring.libraryapi.issue.dto.IssueDto;
import com.jiring.libraryapi.issue.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200/")
public class IssueController {
    @Autowired
    private IssueService issueService;

    public IssueController(IssueService issueService){this.issueService = issueService;}

    @GetMapping("/issues")
    public ResponseEntity<List<IssueDto>> getAllIssues(){
        return new ResponseEntity<List<IssueDto>>(issueService.findAllIssues(),HttpStatus.OK) ;
    }

    @GetMapping("/issues/ongoing/{username}")
    public ResponseEntity<List<IssueDto>> getOnGoingIssues( @PathVariable String username){
        return new ResponseEntity<List<IssueDto>>(issueService.findOnGoingIssuesOfMember(username), HttpStatus.OK);
    }

    @GetMapping("issues/member/{username}")
    public ResponseEntity<List<IssueDto>> getMemberIssues(@PathVariable String username){
        return new ResponseEntity<List<IssueDto>>(issueService.findIssuesByMember(username),HttpStatus.OK);
    }

    @GetMapping("issues/expired")
    public ResponseEntity<List<IssueDto>> getExpiredIssues(){
        return new ResponseEntity<List<IssueDto>>(issueService.findExpiredIssues(),HttpStatus.OK);
    }

/*    @GetMapping("issues/member/{username}/books")
    public List<BookDto> getMemberIssuesBooks(@PathVariable String username){
        return issueService.findIssuesByMember(username);
    }*/

    @GetMapping("/issues/{id}")
    public ResponseEntity<IssueDto> findOneIssue(@PathVariable Long id){
        return new ResponseEntity<IssueDto>(issueService.findIssueByID(id),HttpStatus.OK);
    }

    @PostMapping("/issues")
    public ResponseEntity<Object> createIssue(@RequestBody IssueDto issueDto)
    {
        IssueDto savedIssueDto = issueService.saveIssue(issueDto);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedIssueDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/issues/borrows-eight-days")
    public ResponseEntity<List<DateCount>> findBorrowsInLastDays(){
        return new  ResponseEntity<List<DateCount>>(issueService.findBorrowsInLastEightDays(),HttpStatus.OK);
    }

    @GetMapping("/issues/givebacks-eight-days")
    public ResponseEntity<List<DateCount>> findGiveBacksInLastDays(){
        return new  ResponseEntity<List<DateCount>>(issueService.findGiveBacksInLastEightDays(),HttpStatus.OK);
    }

    @GetMapping("/issues/available-books-count-days")
    public ResponseEntity<List<DateCount>> findAvailableBooksCount(){
        return new  ResponseEntity<List<DateCount>>(issueService.findAvailableBooksCountInLastEightDays(),HttpStatus.OK);
    }

    @GetMapping("/issues/borrowed-books-count-days")
    public ResponseEntity<List<DateCount>> findBorrowedBooksCount(){
        return new  ResponseEntity<List<DateCount>>(issueService.findBorrowedBooksCountInLastEightDays(),HttpStatus.OK);
    }

    @GetMapping("/issues/popular-books")
    public ResponseEntity<List<BookDto>> findPopularBooks(){
        return new  ResponseEntity<List<BookDto>>(issueService.findThreeMostPopularBooks(),HttpStatus.OK);
    }

    @GetMapping("/library-information")
    public ResponseEntity<List<DateCount>> findLibraryInformation(){
        return new  ResponseEntity<List<DateCount>>(issueService.findLibraryInformation(),HttpStatus.OK);
    }
}
