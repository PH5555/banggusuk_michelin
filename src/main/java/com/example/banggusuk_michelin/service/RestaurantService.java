package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.Repository.RestaurantCommentRepository;
import com.example.banggusuk_michelin.Repository.RestaurantImageRepository;
import com.example.banggusuk_michelin.Repository.RestaurantRepository;
import com.example.banggusuk_michelin.dto.RestaurantCreationDto;
import com.example.banggusuk_michelin.entity.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantCommentRepository restaurantCommentRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final GroupRepository groupRepository;
    private final GoogleStorageService googleStorageService;

    @Transactional
    public Map<String, Object> createRestaurant(RestaurantCreationDto restaurantCreationDto, User user) throws Exception {
        Restaurant restaurant = new Restaurant(restaurantCreationDto.getRestaurantName(), restaurantCreationDto.getAddress());

        Optional<Group> group = groupRepository.findGroupById(restaurantCreationDto.getGroupId());
        if(group.isEmpty()){
            throw new Exception("잘못된 group id");
        }

        restaurant.setGroup(group.get());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

//        restaurantCreationDto.getImages().parallelStream().forEach(file ->
//                restaurantImageRepository.save(new RestaurantImage(savedRestaurant, googleStorageService.uploadImage(file))));

        restaurantCommentRepository.save(new RestaurantComment(
                restaurantCreationDto.getComment(),
                restaurantCreationDto.getRating(),
                user,
                savedRestaurant));

        return Map.of("restaurantId", savedRestaurant.getRestaurantId());
    }

    @Transactional
    public List<Restaurant> searchRestaurant(int rating, String groupId) throws Exception {
        Optional<Group> group = groupRepository.findGroupById(groupId);
        if(group.isEmpty()){
            throw new Exception("잘못된 group id");
        }
        return restaurantRepository.findInCurrentGroup(group.get(), rating);
    }
}
