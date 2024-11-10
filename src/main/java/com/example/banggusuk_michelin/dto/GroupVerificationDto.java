package com.example.banggusuk_michelin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupVerificationDto {
    private Boolean status;
    private String message;

    public GroupVerificationDto(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
