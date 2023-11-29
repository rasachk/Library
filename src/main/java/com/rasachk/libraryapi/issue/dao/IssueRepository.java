package com.rasachk.libraryapi.issue.dao;

import com.rasachk.libraryapi.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByBook_Title(String title);

    List<Issue> findAllByMember_Username(String username);

    List<Issue> findAllByMember_UsernameAndReturnDateIsNull(String username);

    Issue findIssueById(Long id);

    List<Issue> findAllByStartDate(LocalDate date);

    List<Issue> findAllByReturnDate(LocalDate date);

    List<Issue> findAllByStartDateAndReturnDateIsNull(LocalDate date);
}
