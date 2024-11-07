package com.rasachk.libraryapi.member.service;

import com.rasachk.libraryapi.member.dto.MemberDto;
import com.rasachk.libraryapi.member.entity.Member;

import java.util.List;

public interface MemberService {
    MemberDto saveNewMember(MemberDto memberDto);

    MemberDto update(MemberDto memberDto);

    void deleteMember(String username);

    MemberDto findMember(String username);

    List<MemberDto> findAllMembers();

    MemberDto convertMemberToDto(Member member);

    Member getMemberEntity(String username);

}
