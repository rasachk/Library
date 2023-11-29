package com.rasachk.libraryapi.member.service;

import com.rasachk.libraryapi.authentication.AuthenticationRequest;
import com.rasachk.libraryapi.authentication.AuthenticationResponse;
import com.rasachk.libraryapi.config.JwtService;
import com.rasachk.libraryapi.exceptions.ResourceNotFoundException;
import com.rasachk.libraryapi.member.dao.MemberRepository;
import com.rasachk.libraryapi.member.dto.MemberDto;
import com.rasachk.libraryapi.member.entity.Role;
import com.rasachk.libraryapi.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;




/*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = findMember(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        member.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        });

        return new User(member.getUsername(), member.getPassword(), authorities);
        //BUG??
    }*/

    public AuthenticationResponse saveMember(MemberDto memberDto) {
        Member member = modelMapper.map(memberDto, Member.class);
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setRole(Role.USER);
        memberRepository.save(member);
        var jwtToken = jwtService.generateToken(member);
        return AuthenticationResponse.builder()
                .username(memberDto.getUsername())
                .role(member.getRole())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Member member = findMember(request.getUsername());
        var jwtToken = jwtService.generateToken(member);
        return AuthenticationResponse.builder()
                .username(request.getUsername())
                .role(member.getRole())
                .token(jwtToken)
                .build();
    }

    public MemberDto convertMemberToDto(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }

    public Member convertDtoToMember(MemberDto memberDto) {
        return findMember(memberDto.getUsername());
        //return modelMapper.map(memberDto,Member.class);
    }

    public List<MemberDto> findAllMembers() {
        return memberRepository.findAllByAvailability(true)
                .stream()
                .map(Member -> modelMapper.map(Member, MemberDto.class))
                .collect(Collectors.toList());
    }

    public Member findMember(String username) {
        Member member = memberRepository.findMemberByUsernameAndAvailability(username, true);
        if (member == null) {
            throw new ResourceNotFoundException("Member", "username", username);
        }
        return member;
    }



    public MemberDto update(MemberDto memberDto) {
        Member member = convertDtoToMember(memberDto);
        member.setUsername(memberDto.getUsername());
        member.setName(memberDto.getName());
        member.setGender(memberDto.getGender());
        member.setAge(memberDto.getAge());
        memberRepository.save(member);
        return memberDto;
    }

    public void deleteMember(String username) {
        Member member = findMember(username);
        member.setAvailability(false);
        memberRepository.save(member);
    }

    public String encryptPassword(String password) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
