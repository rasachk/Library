package com.jiring.libraryapi.issue.service;

import com.jiring.libraryapi.book.dto.BookDto;
import com.jiring.libraryapi.book.entity.Book;
import com.jiring.libraryapi.book.service.BookService;
import com.jiring.libraryapi.exceptions.ResourceNotFoundException;
import com.jiring.libraryapi.issue.dao.IssueRepository;
import com.jiring.libraryapi.issue.dto.DateCount;
import com.jiring.libraryapi.issue.dto.IssueDto;
import com.jiring.libraryapi.issue.entity.Issue;
import com.jiring.libraryapi.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private MemberService memberService;

    public Issue convertDtoToIssue(IssueDto issueDto) {
        Issue issue = new Issue();
        issue.setStartDate(issueDto.getStartDate());
        issue.setReturnDate(issueDto.getReturnDate());
        issue.setPeriod(issueDto.getPeriod());
        issue.setBook(bookService.findBook(issueDto.getBook().getTitle()));
        issue.setMember(memberService.findMember(issueDto.getMember().getUsername()));
        return issue;
    }

    public IssueDto convertIssueToDto(Issue issue) {
        IssueDto issueDto = new IssueDto();
        issueDto.setId(issue.getId());
        issueDto.setStartDate(issue.getStartDate());
        issueDto.setReturnDate(issue.getReturnDate());
        issueDto.setPeriod(issue.getPeriod());
        issueDto.setBook(bookService.convertBookToDto(issue.getBook()));
        issueDto.setMember(memberService.convertMemberToDto(issue.getMember()));
        return issueDto;
    }

    public List<IssueDto> findAllIssues() {
        return issueRepository.findAll()
                .stream()
                .map(this::convertIssueToDto)
                .collect(Collectors.toList());
    }

    public IssueDto findIssueByID(Long id) {
        Issue issue = issueRepository.findIssueById(id);
        if (issue == null) {
            throw new ResourceNotFoundException("Issue", "id", id);
        }
        return convertIssueToDto(issueRepository.findIssueById(id));
    }

    public List<IssueDto> findIssuesByBook(String title) {
        return issueRepository.findAllByBook_Title(title)
                .stream()
                .map(this::convertIssueToDto)
                .collect(Collectors.toList());
    }

    public List<IssueDto> findIssuesByMember(String username) {
        List<Issue> memberIssues = issueRepository.findAllByMember_Username(username);
        if (memberIssues.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return memberIssues
                .stream()
                .map(this::convertIssueToDto)
                .collect(Collectors.toList());
    }

    public IssueDto saveIssue(IssueDto issueDto) {
        if (issueRepository.findIssueById(issueDto.getId()) == null) {
            issueRepository.save(convertDtoToIssue(issueDto));
            bookService.borrowBook(issueDto.getBook());
        } else {
            Issue issue = issueRepository.findIssueById(issueDto.getId());
            issue.setReturnDate(issueDto.getReturnDate());
            issueRepository.save(issue);
            bookService.giveBookBack(issueDto.getBook());
        }

        return issueDto;
    }

    public List<IssueDto> findOnGoingIssuesOfMember(String username) {
        return issueRepository.findAllByMember_UsernameAndReturnDateIsNull(username)
                .stream()
                .map(this::convertIssueToDto)
                .collect(Collectors.toList());
    }

    public List<IssueDto> findExpiredIssues() {
        //TODO ACTS WEIRD DAYS COUNT IS WRONG
        List<Issue> expiredIssues = new ArrayList<>();
        Period period;
        for (Issue issue : issueRepository.findAll()) {
            if (issue.getReturnDate() == null) {
                period = Period.between(issue.getStartDate(), LocalDate.now());
            } else {
                period = Period.between(issue.getStartDate(), issue.getReturnDate());
            }

            if (period.getDays() > issue.getPeriod()) {
                expiredIssues.add(issue);
            }
        }

        return expiredIssues
                .stream()
                .map(this::convertIssueToDto)
                .collect(Collectors.toList());
    }

    public ArrayList<DateCount> findBorrowsInLastEightDays() {
        LocalDate startDate = LocalDate.now().plusDays(-7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        ArrayList<DateCount> arrayList = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            arrayList.add(new DateCount(date, issueRepository.findAllByStartDate(date).size()));
        }
        return arrayList;
    }

    public ArrayList<DateCount> findGiveBacksInLastEightDays() {
        LocalDate startDate = LocalDate.now().plusDays(-7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        ArrayList<DateCount> arrayList = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            arrayList.add(new DateCount(date, issueRepository.findAllByReturnDate(date).size()));
        }
        return arrayList;
    }

    public ArrayList<DateCount> findAvailableBooksCountInLastEightDays() {
        Integer bookCount;
        LocalDate libraryStartDate = LocalDate.parse("2023-04-01");
        LocalDate startDate = LocalDate.now().plusDays(-7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        ArrayList<DateCount> arrayList = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            bookCount = bookService.findBooksCount();
            for (LocalDate localDate = libraryStartDate; localDate.isBefore(date.plusDays(1)); localDate = localDate.plusDays(1)) {
                //TODO HAME KATABARO MIGE
                bookCount -= issueRepository.findAllByStartDateAndReturnDateIsNull(localDate).size();
            }
            arrayList.add(new DateCount(date, bookCount));
        }

        return arrayList;
    }

    public ArrayList<DateCount> findBorrowedBooksCountInLastEightDays() {
        LocalDate libraryStartDate = LocalDate.parse("2023-04-01");
        LocalDate startDate = LocalDate.now().plusDays(-7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        ArrayList<DateCount> arrayList = new ArrayList<>();
        Integer borrowedBooksCount;
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            borrowedBooksCount = 0;
            for (LocalDate localDate = libraryStartDate; localDate.isBefore(date); localDate = localDate.plusDays(1)) {
                borrowedBooksCount += issueRepository.findAllByStartDateAndReturnDateIsNull(localDate).size();
            }
            arrayList.add(new DateCount(date, borrowedBooksCount));
        }
        return arrayList;
    }

    public ArrayList<BookDto> findThreeMostPopularBooks() {
        //TODO KETAB TEKRARI
        ArrayList<Book> issuedBooks = new ArrayList<>();
        ArrayList<BookDto> popularBooks = new ArrayList<>();
        for (Issue issue : issueRepository.findAll()) {
            issuedBooks.add(issue.getBook());
        }
        for (Integer counter = 0; counter < 3; counter++) {
            Book mostIssuedBook
                    = issuedBooks.stream()
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .get()
                    .getKey();

            popularBooks.add(bookService.convertBookToDto(mostIssuedBook));
            issuedBooks.remove(mostIssuedBook);
        }
        return popularBooks;
    }

    public ArrayList<DateCount> findLibraryInformation(){
        //TODO MAKE IT MORE CLEAR
        ArrayList<DateCount> arrayList = new ArrayList<>();
        arrayList.add(new DateCount(LocalDate.now(),bookService.findBooksCount()));
        arrayList.add(new DateCount(LocalDate.now(),memberService.findAllMembers().size()));
        arrayList.add(new DateCount(LocalDate.now(),findAllIssues().size()));
        return arrayList;
    }

}
