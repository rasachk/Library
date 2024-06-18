package com.rasachk.libraryapi.member.controller;

import com.rasachk.libraryapi.authentication.AuthenticationResponse;
import com.rasachk.libraryapi.member.dto.MemberDto;
import com.rasachk.libraryapi.member.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api
public class MemberController {
    @Autowired
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/members/create")
    public ResponseEntity<AuthenticationResponse> createMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<AuthenticationResponse>(memberService.saveMember(memberDto), HttpStatus.OK);
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
//        return new ResponseEntity<AuthenticationResponse>(memberService.authenticate(request), HttpStatus.OK);
//    }

    @GetMapping("/members/list")
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return new ResponseEntity<List<MemberDto>>(memberService.findAllMembers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberDto> findOneMember(@PathVariable String username) {
        return new ResponseEntity<MemberDto>(memberService.convertMemberToDto(memberService.findMember(username)), HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<MemberDto> updateMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<MemberDto>(memberService.update(memberDto), HttpStatus.OK);
    }

    /*    @PatchMapping("/members/{username}")
        public ResponseEntity<MemberDto> updateMember(@PathVariable String username, @RequestBody JsonPatch patch){
            return ResponseEntity<MemberDto>(memberService.updateMember();)
        }*/
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteMember(@PathVariable("username") String username) {
        memberService.deleteMember(username);
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }
}
