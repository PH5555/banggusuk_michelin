package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.Repository.RestaurantCommentRepository;
import com.example.banggusuk_michelin.Repository.RestaurantGroupRepository;
import com.example.banggusuk_michelin.Repository.RestaurantRepository;
import com.example.banggusuk_michelin.dto.RestaurantCreationDto;
import com.example.banggusuk_michelin.dto.RestaurantDto;
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
    private final GroupRepository groupRepository;
    private final GoogleStorageService googleStorageService;
    private final RestaurantGroupRepository restaurantGroupRepository;
    private final EntityManager em;

    @Transactional
    public Map<String, Object> createRestaurant(RestaurantCreationDto restaurantCreationDto, MultipartFile file, User user) throws Exception {
        Optional<Group> group = groupRepository.findGroupById(restaurantCreationDto.getGroupId());
        if(group.isEmpty()){
            throw new Exception("잘못된 group id");
        }

        Optional<Restaurant> restaurant = restaurantRepository.findByAddress(restaurantCreationDto.getAddress());
        if(restaurant.isEmpty()){
            Restaurant newRestaurant = new Restaurant(restaurantCreationDto.getRestaurantName(), restaurantCreationDto.getAddress(), restaurantCreationDto.getLatitude(), restaurantCreationDto.getLongitude());
            if(file != null){
                newRestaurant.setImage(googleStorageService.uploadImage(file));
            }
            restaurant = Optional.of(restaurantRepository.save(newRestaurant));
        }

        RestaurantComment restaurantComment = new RestaurantComment(
                restaurantCreationDto.getComment(),
                restaurantCreationDto.getRating());
        restaurantComment.setUser(user);

        RestaurantComment savedComment = restaurantCommentRepository.save(restaurantComment);

        Optional<RestaurantGroup> restaurantGroup = restaurantGroupRepository.searchRestaurantByGroupId(restaurant.get(), group.get().getGroupId());
        if(restaurantGroup.isEmpty()){
            restaurantGroupRepository.save(restaurant.get(), group.get(), savedComment);
        }
        else {
            restaurantGroupRepository.addComment(restaurantGroup.get(), savedComment);
        }

        em.flush();

        return Map.of("restaurantId", restaurant.get().getRestaurantId());
    }

    @Transactional
    public List<RestaurantDto> searchRestaurant(int rating, String groupId) throws Exception {
        Optional<Group> group = groupRepository.findGroupById(groupId);
        if(group.isEmpty()){
            throw new Exception("잘못된 group id");
        }
        List<RestaurantGroup> restaurants = restaurantGroupRepository.findInCurrentGroup(group.get(), rating);
        return restaurants.stream().map(RestaurantDto::of).toList();
    }
}
