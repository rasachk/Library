package com.rasachk.libraryapi.member.service;

import com.rasachk.libraryapi.exceptions.ResourceNotFoundException;
import com.rasachk.libraryapi.member.dao.MemberRepository;
import com.rasachk.libraryapi.member.dto.MemberDto;
import com.rasachk.libraryapi.member.entity.Member;
import com.rasachk.libraryapi.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public MemberDto saveNewMember(MemberDto memberDto) {
        Member newMember = modelMapper.map(memberDto, Member.class);
        newMember.setRole(Role.USER);
        newMember.setPassword(encryptPassword(memberDto.getPassword()));
        memberRepository.save(newMember);
        return convertMemberToDto(newMember);
    }


    public MemberDto convertMemberToDto(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }

    public List<MemberDto> findAllMembers() {
        return memberRepository.findAllByAvailability(true)
                .stream()
                .map(Member -> modelMapper.map(Member, MemberDto.class))
                .collect(Collectors.toList());
    }

    public MemberDto findMember(String username) {
        Member member = memberRepository.findMemberByUsernameAndAvailability(username, true);
        if (member == null) {
            throw new ResourceNotFoundException("Member", "username", username);
        }
        return convertMemberToDto(member);
    }


    public MemberDto update(MemberDto memberDto) {
        Member member = memberRepository.findMemberByUsernameAndAvailability(memberDto.getUsername(), true);
        member.setUsername(memberDto.getUsername());
        member.setName(memberDto.getName());
        member.setGender(memberDto.getGender());
        member.setAge(memberDto.getAge());
        memberRepository.save(member);
        return memberDto;
    }

    public void deleteMember(String username) {
        Member member = memberRepository.findMemberByUsernameAndAvailability(username, true);
        member.setAvailability(false);
        memberRepository.save(member);
    }

    private String encryptPassword(String password) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
