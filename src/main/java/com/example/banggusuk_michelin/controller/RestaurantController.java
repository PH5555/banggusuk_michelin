package com.example.banggusuk_michelin.controller;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.dto.RestaurantCreationDto;
import com.example.banggusuk_michelin.dto.RestaurantDto;
import com.example.banggusuk_michelin.entity.Restaurant;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.service.RestaurantService;
import com.google.protobuf.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping()
    public ApiResponse<Map<String, Object>> createRestaurant(RestaurantCreationDto restaurantCreationDto, @RequestParam(required = false) MultipartFile file, @AuthenticationPrincipal User user){
        log.info(restaurantCreationDto.getRestaurantName());
        try {
            return ApiResponse.success(restaurantService.createRestaurant(restaurantCreationDto, file, user));
        } catch (Exception e) {
            return ApiResponse.fail(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping()
    public ApiResponse<Object> viewRestaurant(@RequestParam("rating") int rating, @RequestParam("groupId") String groupId) {
        try {
            List<RestaurantDto> result = restaurantService.searchRestaurant(rating, groupId);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.fail(Map.of("message", e.getMessage()));
        }
    }
}
