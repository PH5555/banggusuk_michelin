package com.example.banggusuk_michelin.dto;

import lombok.Getter;

@Getter
public class KakaoTokenDto {
    private String token_type;
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String scope;
}
