package com.jiring.libraryapi.member.dao;

import com.jiring.libraryapi.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member,Long> {

    List<Member> findAllByAvailability(Boolean availability);

    Member findMemberByUsernameAndAvailability(String username, Boolean availability);
}
