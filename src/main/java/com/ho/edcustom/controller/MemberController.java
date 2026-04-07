package com.ho.edcustom.controller;

import com.ho.edcustom.DTO.Request.*;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.Jwt.JwtTokenProvider;
import com.ho.edcustom.service.FireBaseService;
import com.ho.edcustom.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    public ResponseEntity<HttpResponse>  register(@RequestBody RegisterRequest DTO)
    {
        HttpResponse Response =memberService.createMember(DTO.getName(),DTO.getProfileImage(),DTO.getNickname(),DTO.getEmail(), DTO.getPassword());

        return new ResponseEntity<>(Response,Response.getStatus());
    }
    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest DTO) {
        HttpResponse Response =memberService.loginMember(DTO.getEmail(),DTO.getPassword());

        //HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("abc","abc");

        return new ResponseEntity<>(Response,Response.getStatus());
    }

    @PostMapping("/findbodybytoken")
    public ResponseEntity<HttpResponse> findBodyByToken(@RequestBody TokenRequest DTO)
    {
        HttpResponse Response =jwtTokenProvider.getClaimsFromToken(DTO.getToken());
        return new ResponseEntity<>(Response,Response.getStatus());
    }

    @PostMapping("/alreadyusingemail")
    public boolean alreadyUsingEmail(@RequestBody EmailRequest DTO)
    {
        return memberService.alreadyUsingemail(DTO.getEmail());
    }

    @PostMapping("/alreadyusingnickname")
    public boolean alreadyUsingNickName(@RequestBody NickNameRequest DTO)
    {
        return memberService.alreadyUsingnickname(DTO.getNickname());
    }

    @PostMapping("/updatenickname")
    public ResponseEntity<HttpResponse> updateNickName(@RequestBody UpdateRequest DTO)
    {
        HttpResponse Response = memberService.updatenickname(DTO.getEmail(),DTO.getNickname());
        return new ResponseEntity<>(Response,Response.getStatus());
    }

    @PatchMapping("/changepassword")
    public ResponseEntity<HttpResponse> updatePassword(@RequestBody PasswordRequest DTO)
    {

        HttpResponse Response = memberService.updatePassword(DTO.getEmail(), DTO.getCurrentPassword(),DTO.getNewPassword());
        return new ResponseEntity<>(Response,Response.getStatus());
    }

    @GetMapping("/hello")
    public String hellocontroller(){
        return "hello world";

    }
}

