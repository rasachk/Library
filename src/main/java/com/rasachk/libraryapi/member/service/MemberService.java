package com.rasachk.libraryapi.member.service;

import com.rasachk.libraryapi.authentication.AuthenticationResponse;
import com.rasachk.libraryapi.member.dto.MemberDto;
import com.rasachk.libraryapi.member.entity.Member;

import java.util.List;

public interface MemberService {
    AuthenticationResponse saveMember(MemberDto memberDto);

    MemberDto update(MemberDto memberDto);

    void deleteMember(String username);

    Member findMember(String username);

    List<MemberDto> findAllMembers();

    MemberDto convertMemberToDto(Member member);

}
