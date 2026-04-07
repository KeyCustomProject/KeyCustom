package com.ho.edcustom.service;

import com.google.api.Http;
import com.ho.edcustom.DTO.LikeDTO;
import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.entity.Like;
import com.ho.edcustom.entity.Member;
import com.ho.edcustom.entity.SharedItem;
import com.ho.edcustom.enumSet.ErrorCode;
import com.ho.edcustom.repository.LikeRepository;
import com.ho.edcustom.repository.MemberRepository;
import com.ho.edcustom.repository.SharedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final SharedItemRepository sharedItemRepository;


    @Transactional
    public HttpResponse like(Long memberid,Long shareditemid)
    {
        if(memberid == null || shareditemid == null)
        {
            return new HttpResponse(HttpStatus.BAD_REQUEST,ErrorCode.BAD_REQUEST,null);
        }


        Optional<Member> member = memberRepository.findById(memberid);
        if (member.isEmpty()) {
            return new HttpResponse(HttpStatus.NOT_FOUND,ErrorCode.MEMBER_NOT_FOUND,null);
        }
        Optional<SharedItem> sharedItem = sharedItemRepository.findById(shareditemid);
        if (sharedItem.isEmpty()) {
            return new HttpResponse(HttpStatus.NOT_FOUND,ErrorCode.ITEM_NOT_FOUND,null);
        }

        Optional<Like> dup_like = likeRepository.findByMemberAndSharedItem(member.get(),sharedItem.get());
        SharedItem share = sharedItem.get();
        if(dup_like.isPresent())
        {

            likeRepository.delete(dup_like.get());
            share.decrease();
            sharedItemRepository.save(share);
            return new HttpResponse(HttpStatus.OK,ErrorCode.LIKE_DELETE,null);
        }

        Like like = Like.builder()
                .member(member.get())
                .sharedItem(sharedItem.get())
                .build();
        share.increase();
        likeRepository.save(like);
        return new HttpResponse(HttpStatus.OK,ErrorCode.LIKE_INSERT,null);
    }


    public HttpResponse memberliked(Long memberid)
    {
        List<LikeDTO> list = likeRepository.findDTOByMemberId(memberid);

        if (list.isEmpty()) {
            return new HttpResponse(HttpStatus.NOT_FOUND,ErrorCode.MEMBER_NOT_FOUND,null);
        }

        return new HttpResponse(HttpStatus.OK,ErrorCode.SUCCESS,list);
    }


    public HttpResponse checkliked(Long memberid)
    {;
        List<SharedItem> list = likeRepository.findSharedItemsByMemberId(memberid);
        if (list.isEmpty()) {
            return new HttpResponse(HttpStatus.NOT_FOUND,ErrorCode.MEMBER_NOT_FOUND,null);
        }

        return new HttpResponse(HttpStatus.OK,ErrorCode.SUCCESS,list);
    }
}
