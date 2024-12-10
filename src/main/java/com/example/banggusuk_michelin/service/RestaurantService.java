package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.Repository.RestaurantCommentRepository;
import com.example.banggusuk_michelin.Repository.RestaurantRepository;
import com.example.banggusuk_michelin.dto.RestaurantCreationDto;
import com.example.banggusuk_michelin.dto.RestaurantDto;
import com.example.banggusuk_michelin.entity.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantCommentRepository restaurantCommentRepository;
    private final GroupRepository groupRepository;
    private final GoogleStorageService googleStorageService;
    private final EntityManager em;

    @Transactional
    public Map<String, Object> createRestaurant(RestaurantCreationDto restaurantCreationDto, User user) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findByAddress(restaurantCreationDto.getAddress());
        if(restaurant.isEmpty()){
            Restaurant newRestaurant = new Restaurant(restaurantCreationDto.getRestaurantName(), restaurantCreationDto.getAddress());
            if(restaurantCreationDto.getImage() != null){
                newRestaurant.setImage(googleStorageService.uploadImage(restaurantCreationDto.getImage()));
            }
            restaurant = Optional.of(newRestaurant);
        }

        Optional<Group> group = groupRepository.findGroupById(restaurantCreationDto.getGroupId());
        if(group.isEmpty()){
            throw new Exception("잘못된 group id");
        }

        restaurant.get().setGroup(group.get());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant.get());

        RestaurantComment restaurantComment = new RestaurantComment(
                restaurantCreationDto.getComment(),
                restaurantCreationDto.getRating());

        restaurantComment.setRestaurant(savedRestaurant);
        restaurantComment.setUser(user);

        restaurantCommentRepository.save(restaurantComment);
        em.flush();

        return Map.of("restaurantId", savedRestaurant.getRestaurantId());
    }

    @Transactional
    public List<RestaurantDto> searchRestaurant(int rating, String groupId) throws Exception {
        Optional<Group> group = groupRepository.findGroupById(groupId);
        if(group.isEmpty()){
            throw new Exception("잘못된 group id");
        }
        List<Restaurant> restaurants = restaurantRepository.findInCurrentGroup(group.get(), rating);
        return restaurants.parallelStream().map(RestaurantDto::of).toList();
    }
}
