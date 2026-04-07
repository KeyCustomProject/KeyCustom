package com.ho.edcustom.service;

import com.ho.edcustom.DTO.Response.HttpResponse;
import com.ho.edcustom.DTO.Response.TokenResponse;
import com.ho.edcustom.Jwt.JwtTokenProvider;
import com.ho.edcustom.entity.Member;
import com.ho.edcustom.enumSet.ErrorCode;
import com.ho.edcustom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final FireBaseService fireBaseService;

    public HttpResponse createMember(String name,String profileImage,String nickname,String email,String password){

        if(alreadyUsingemail(email))
        {
            return new HttpResponse(HttpStatus.BAD_REQUEST,ErrorCode.BAD_REQUEST_DUPLICATION,null);
        }

        String imageUrl =fireBaseService.uploadProfile(profileImage);


        memberRepository.save(Member.builder()
                .name(name)
                .imageUrl(imageUrl)
                .nickname(nickname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build());
        return new HttpResponse(HttpStatus.CREATED,ErrorCode.CREATED,null);
    }

    public boolean alreadyUsingemail(String email)
    {
        return memberRepository.findByEmail(email).isPresent();
    }
    public HttpResponse updatenickname(String email,String nickname)
    {
        Optional<Member> member = memberRepository.findByEmail(email);
        if ((alreadyUsingnickname(nickname)))
        {
            return new HttpResponse(HttpStatus.BAD_REQUEST,ErrorCode.NICKNAME_DUPLICATION,null);
        }
        else
        {
            Member updateMember = member.get().toBuilder()
                    .nickname(nickname)
                    .build();

            memberRepository.save(updateMember);
            return new HttpResponse(HttpStatus.OK, ErrorCode.SUCCESS, null);
        }

    }

    public boolean alreadyUsingnickname(String nickname)
    {
        return memberRepository.findByNickname(nickname).isPresent();

    }

    public HttpResponse loginMember(String email, String password) {
            Optional<Member> member = memberRepository.findByEmail(email);
            if (member.isPresent()) {
                if (!passwordEncoder.matches(password, member.get().getPassword()))
                {
                    return new HttpResponse(HttpStatus.BAD_REQUEST,ErrorCode.LOGIN_BAD_REQUEST,null);
                }
            }
            else
            {
                return new HttpResponse(HttpStatus.BAD_REQUEST,ErrorCode.LOGIN_BAD_REQUEST,null);
            }
        TokenResponse token = new TokenResponse(jwtTokenProvider.generateToken(member.get()));
        return new HttpResponse(HttpStatus.OK,ErrorCode.SUCCESS,token);
    }


//    public boolean confirmPassword(String email, String password)
//    {
//        Optional<Member> member = memberRepository.findByEmail(email);
//        return member.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();
//    }
    public HttpResponse updatePassword(String email,String currentPassword,String newPassword)
    {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            if (passwordEncoder.matches(currentPassword, member.get().getPassword())) {

                Member updateMember = member.get().toBuilder()
                        .password(passwordEncoder.encode(newPassword))
                        .build();

                memberRepository.save(updateMember);
                return new HttpResponse(HttpStatus.OK, ErrorCode.SUCCESS, null);
            }
        }
        return new HttpResponse(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, null);
    }
}
