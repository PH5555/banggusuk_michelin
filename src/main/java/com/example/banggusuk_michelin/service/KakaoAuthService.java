package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.UserRepository;
import com.example.banggusuk_michelin.dto.KakaoUserInfoResponse;
import com.example.banggusuk_michelin.entity.User;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class KakaoAuthService {

    private final KakaoUserInfo kakaoUserInfo;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public String isSignedUp(String token) {
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);
        User user = userRepository.findByKeyCode(userInfo.getId().toString());
        return user.getUid();
    }
}
