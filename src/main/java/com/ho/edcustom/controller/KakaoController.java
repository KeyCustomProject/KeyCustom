package com.ho.edcustom.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ho.edcustom.DTO.Request.KakaoLoginRequest;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.enumSet.ErrorCode;
import com.ho.edcustom.service.KakaoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;
    @ResponseBody
    @PostMapping("/social/kakao/login")
    public HttpResponse kakaoLogin(@RequestBody KakaoLoginRequest DTO) throws JsonProcessingException {
        return new HttpResponse(HttpStatus.OK,ErrorCode.SUCCESS,kakaoService.kakaoLogin(DTO.getCode()));
    }
    
}

