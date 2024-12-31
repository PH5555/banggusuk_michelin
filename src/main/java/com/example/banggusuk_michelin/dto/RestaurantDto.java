package com.example.banggusuk_michelin.dto;

import com.example.banggusuk_michelin.entity.Restaurant;
import com.example.banggusuk_michelin.entity.RestaurantComment;
import com.example.banggusuk_michelin.entity.RestaurantGroup;
import com.example.banggusuk_michelin.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RestaurantDto {
    private final String restaurantName;
    private final String restaurantAddress;
    private final String images;
    private final String latitude;
    private final String longitude;
    private final List<RestaurantCommentDto> restaurantCommentDto;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (RestaurantCommentDto commentDto : restaurantCommentDto) {
            result.append(commentDto.getComment() + ", ");
        }

        return "RestaurantDto [restaurantName=" + restaurantName + ", restaurantAddress=" + restaurantAddress + ", comment=" + result + "]";
    }

    public static RestaurantDto of(RestaurantGroup restaurantGroup){
        return new RestaurantDto(
                restaurantGroup.getRestaurant().getRestaurantName(),
                restaurantGroup.getRestaurant().getRestaurantAddress(),
                restaurantGroup.getRestaurant().getImage(),
                restaurantGroup.getRestaurant().getLatitude(),
                restaurantGroup.getRestaurant().getLongitude(),
                restaurantGroup.getRestaurantComments().stream().map(comment -> RestaurantCommentDto.of(comment, comment.getUser())).toList()
        );
    }
}
