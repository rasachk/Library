package com.rasachk.libraryapi.member.controller;

import com.rasachk.libraryapi.authentication.AuthenticationResponse;
import com.rasachk.libraryapi.member.dto.MemberDto;
import com.rasachk.libraryapi.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/create")
    public ResponseEntity<AuthenticationResponse> createMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.saveMember(memberDto), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<MemberDto> updateMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.update(memberDto), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteMember(@PathVariable("username") String username) {
        memberService.deleteMember(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/members/list")
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return new ResponseEntity<>(memberService.findAllMembers(), HttpStatus.OK);
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<MemberDto> findOneMember(@PathVariable String username) {
//        return new ResponseEntity<>(memberService.findMember(username), HttpStatus.OK);
//    }


}
