package com.example.banggusuk_michelin.controller;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.dto.LoginDto;
import com.example.banggusuk_michelin.jwt.JwtTokenProvider;
import com.example.banggusuk_michelin.service.KakaoAuthService;
import com.example.banggusuk_michelin.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KakaoService kakaoService;

    // 카카오 로그인을 위해 회원가입 여부 확인, 이미 회원이면 Jwt 토큰 발급
//    @PostMapping("/login")
//    public ApiResponse<HashMap<String, String>> authCheck(@RequestHeader String accessToken) {
//        String userId = kakaoAuthService.isSignedUp(accessToken); // 유저 고유번호 추출
//        HashMap<String, String> map = new HashMap<>();
//        map.put(userId, jwtTokenProvider.createToken(userId));
//        return ApiResponse.success(map);
//    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(LoginDto loginDto){
        log.info(loginDto.getCode());
        return ApiResponse.success(kakaoService.login(loginDto));
    }
}