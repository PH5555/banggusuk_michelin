package com.example.banggusuk_michelin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RestaurantCreationDto {
    private String restaurantName;
    private String address;
    private List<MultipartFile> images;
    private String comment;
    private int rating;
    private String groupId;
}
