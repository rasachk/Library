package com.rasachk.libraryapi.member.controller;

import com.rasachk.libraryapi.member.model.dto.MemberDto;
import com.rasachk.libraryapi.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/saveNewMember")
    public ResponseEntity<MemberDto> saveNewMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.saveNewMember(memberDto), HttpStatus.OK);
    }

    @PostMapping("/updateMember")
    public ResponseEntity<MemberDto> updateMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.update(memberDto), HttpStatus.OK);
    }

    @DeleteMapping("/deleteMember/{username}")
    public ResponseEntity<Void> deleteMember(@PathVariable("username") String username) {
        memberService.deleteMember(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllMembers")
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return new ResponseEntity<>(memberService.findAllMembers(), HttpStatus.OK);
    }

    @GetMapping("/findMember/{username}")
    public ResponseEntity<MemberDto> findOneMember(@PathVariable String username) {
        return new ResponseEntity<>(memberService.findMember(username), HttpStatus.OK);
    }


}
