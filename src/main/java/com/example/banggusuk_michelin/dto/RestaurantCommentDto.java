package com.example.banggusuk_michelin.dto;

import com.example.banggusuk_michelin.entity.RestaurantComment;
import com.example.banggusuk_michelin.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestaurantCommentDto {
    private final String comment;
    private final int rating;
    private final UserDto user;

    public static RestaurantCommentDto of(RestaurantComment restaurantComment, User user){
        return new RestaurantCommentDto(
                restaurantComment.getComment(),
                restaurantComment.getRating(),
                UserDto.of(user)
        );
    }
}
