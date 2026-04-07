package com.ho.edcustom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ho.edcustom.DTO.Response.TokenResponse;
import com.ho.edcustom.DTO.SocialUserInfo;
import com.ho.edcustom.Jwt.JwtTokenProvider;
import com.ho.edcustom.entity.Member;
import com.ho.edcustom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;
    public TokenResponse kakaoLogin(String DTO) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(DTO);

        // 2. 토큰으로 카카오 API 호출
        SocialUserInfo kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 카카오ID로 회원가입 처리
        Member kakaoUser = registerKakaoUserIfNeed(kakaoUserInfo);

        String token=jwtTokenProvider.generateToken(kakaoUser);

        TokenResponse tokenResponse = new TokenResponse(token);

        return tokenResponse;
    }
    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id",clientId );
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }
    // 2. 토큰으로 카카오 API 호출
    private SocialUserInfo getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        JsonNode propertiesNode = jsonNode.get("properties");
        String nickname;
        if (propertiesNode != null) {
            JsonNode nicknameNode = propertiesNode.get("nickname");
            if (nicknameNode != null) {
                nickname = nicknameNode.asText();
                // Use the nickname as needed
            } else {
                // Handle the case where "nickname" is not found
                nickname ="NNN";//NotNickName
            }
        } else {
            // Handle the case where "properties" is not found
            nickname ="NNN";//NotNickName
        }


        return new SocialUserInfo(id, nickname, email);
    }
    // 3. 카카오ID로 회원가입 처리
    private Member registerKakaoUserIfNeed (SocialUserInfo kakaoUserInfo) {
        // DB 에 중복된 email이 있는지 확인
        String kakaoEmail = kakaoUserInfo.getEmail();
        String nickname = kakaoUserInfo.getNickname();
        Member kakaoUser = memberRepository.findByEmail(kakaoEmail)
                .orElse(null);

        if (kakaoUser == null) {
            // 회원가입
            // password: random UUID
            String password = UUID.randomUUID().toString();
            //String encodedPassword = passwordEncoder.encode(password);

            //String profile = "https://ossack.s3.ap-northeast-2.amazonaws.com/basicprofile.png";
            memberRepository.save(Member.builder()
                    .name(nickname)
                    .email(kakaoEmail)
                    .password(password)
                    .build());

        }
        return kakaoUser;
    }

}