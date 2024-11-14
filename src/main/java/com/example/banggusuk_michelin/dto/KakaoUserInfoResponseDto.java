package com.example.banggusuk_michelin.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfoResponseDto {

    private Long id;
    private String connected_at;
    private KakaoPropertiesDto properties;
    private KakaoAccountDto kakao_account;
}
