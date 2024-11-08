package com.example.banggusuk_michelin.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupCreationDto {
    private String groupName;
    private String groupImage; // TODO:파일
    private String password;
}
