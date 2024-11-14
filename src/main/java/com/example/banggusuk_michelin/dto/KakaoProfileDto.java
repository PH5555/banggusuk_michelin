package com.example.banggusuk_michelin.dto;

import lombok.Getter;

@Getter
public class KakaoProfileDto {

    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
    private Boolean is_default_image;
}