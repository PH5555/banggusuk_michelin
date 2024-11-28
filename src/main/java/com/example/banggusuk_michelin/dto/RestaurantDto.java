package com.example.banggusuk_michelin.dto;

import com.example.banggusuk_michelin.entity.Restaurant;
import com.example.banggusuk_michelin.entity.RestaurantComment;
import com.example.banggusuk_michelin.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RestaurantDto {
    private final String restaurantName;
    private final String restaurantAddress;
//    private final String images;
    private final List<RestaurantCommentDto> restaurantCommentDto;

    public static RestaurantDto of(Restaurant restaurant){
        return new RestaurantDto(
                restaurant.getRestaurantName(),
                restaurant.getRestaurantAddress(),
                restaurant.getComments().stream().map(comment -> RestaurantCommentDto.of(comment, comment.getUser())).toList()
        );
    }
}
