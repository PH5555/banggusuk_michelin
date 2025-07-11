package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.UserRepository;
import com.example.banggusuk_michelin.dto.KakaoTokenDto;
import com.example.banggusuk_michelin.dto.KakaoUserInfoResponseDto;
import com.example.banggusuk_michelin.dto.LoginDto;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoService {
    private final UserRepository userRepository;
    private final KakaoUserInfo kakaoUserInfo;
    private final JwtTokenProvider jwtTokenProvider;
    private final WebClient webClient;
    private String uri = "https://kauth.kakao.com/oauth/token";

    @Value("${kakao.key.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Transactional
    public Map<String, Object> login(LoginDto loginDto) {

        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(loginDto.getCode(), redirectUri);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoResponseDto userInfo = kakaoUserInfo.getUserInfo(accessToken);

        //3. 카카오ID로 회원가입 & 로그인 처리
        return kakaoUserLogin(userInfo);
    }

    public String getAccessToken(String code, String redirectUri) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        Flux<KakaoTokenDto> response = webClient.post()
                .uri(uri)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(KakaoTokenDto.class);

        return response.blockFirst().getAccess_token();
    }

    public Map<String, Object> kakaoUserLogin(KakaoUserInfoResponseDto userInfo){

        String keyCode = userInfo.getId().toString();
        String nickname = userInfo.getKakao_account().getProfile().getNickname();

        Optional<User> user = userRepository.findByKeyCode(userInfo.getId().toString());

        String uid;
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setKeyCode(keyCode);
            newUser.setNickname(nickname);
            uid = userRepository.save(newUser).getUid();
        }
        else{
            uid = user.get().getUid();
        }
        //토큰 생성
        String token = jwtTokenProvider.createToken(uid);

        return Map.of("uid", uid, "nickname", nickname, "token", token);
    }

}