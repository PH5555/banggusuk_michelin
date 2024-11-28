package com.example.banggusuk_michelin.dto;

import com.example.banggusuk_michelin.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {
    private final String uid;
    private final String nickname;

    public static UserDto of(User user){
        return new UserDto(
                user.getUid(),
                user.getUsername()
        );
    }
}
