package com.example.banggusuk_michelin.dto;

import lombok.Getter;

@Getter
public class KakaoAccountDto {

    private Boolean profile_nickname_needs_agreement;
    private Boolean profile_image_needs_agreement;
    private KakaoProfileDto profile;
}