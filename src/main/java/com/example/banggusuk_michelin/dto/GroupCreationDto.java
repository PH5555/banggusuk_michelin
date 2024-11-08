package com.example.banggusuk_michelin.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GroupCreationDto {
    private String groupName;
    private MultipartFile groupImage;
    private String password;
}
