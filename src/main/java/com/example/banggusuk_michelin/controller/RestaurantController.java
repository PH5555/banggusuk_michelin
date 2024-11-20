package com.example.banggusuk_michelin.controller;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.dto.RestaurantCreationDto;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.service.RestaurantService;
import com.google.protobuf.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping()
    public ApiResponse<Map<String, Object>> createRestaurant(@RequestBody RestaurantCreationDto restaurantCreationDto, @AuthenticationPrincipal User user){
        try {
            return ApiResponse.success(restaurantService.createRestaurant(restaurantCreationDto, user));
        } catch (Exception e) {
            return ApiResponse.fail(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping()
    public ApiResponse<Object> viewRestaurant(@RequestParam int rating, @RequestParam String groupId) {
        try {
            return ApiResponse.success(restaurantService.searchRestaurant(rating, groupId));
        } catch (Exception e) {
            return ApiResponse.fail(Map.of("message", e.getMessage()));
        }
    }
}
