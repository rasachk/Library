package com.rasachk.libraryapi.member.dao;

import com.rasachk.libraryapi.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {

    List<Member> findAllByAvailability(Boolean availability);

    Member findMemberByUsernameAndAvailability(String username, Boolean availability);
}
