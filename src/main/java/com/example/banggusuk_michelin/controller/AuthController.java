package com.example.banggusuk_michelin.controller;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.dto.LoginDto;
import com.example.banggusuk_michelin.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KakaoService kakaoService;

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(LoginDto loginDto){
        log.info(loginDto.getCode());
        return ApiResponse.success(kakaoService.login(loginDto));
    }
}