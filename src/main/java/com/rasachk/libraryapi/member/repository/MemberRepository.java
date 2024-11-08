package com.rasachk.libraryapi.member.repository;

import com.rasachk.libraryapi.member.model.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    List<Member> findAllByAvailability(Boolean availability);

    Member findMemberByUsernameAndAvailability(String username, Boolean availability);
}
