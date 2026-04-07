package com.ho.edcustom.controller;

import com.ho.edcustom.DTO.Request.ItemRequest;
import com.ho.edcustom.DTO.Request.LikeRequest;
import com.ho.edcustom.DTO.Request.LikedMemberRequest;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<HttpResponse> likeitem(@RequestBody LikeRequest DTO) {
        HttpResponse Response = likeService.like(DTO.getMemberid(), DTO.getShareditemid());

        return new ResponseEntity<>(Response, Response.getStatus());
    }

    @PostMapping("/memberliked")
    public ResponseEntity<HttpResponse> memberliked(@RequestBody LikedMemberRequest DTO) {
        HttpResponse Response = likeService.memberliked(DTO.getMemberid());

        return new ResponseEntity<>(Response, Response.getStatus());
    }
    @PostMapping("/checkliked")
    public ResponseEntity<HttpResponse> checkliked(@RequestBody LikedMemberRequest DTO) {
        HttpResponse Response = likeService.checkliked(DTO.getMemberid());

        return new ResponseEntity<>(Response, Response.getStatus());
    }

}
