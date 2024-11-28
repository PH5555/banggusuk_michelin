package com.example.banggusuk_michelin.restaurant;

import com.example.banggusuk_michelin.MockCustomUser;
import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.Repository.RestaurantCommentRepository;
import com.example.banggusuk_michelin.Repository.RestaurantRepository;
import com.example.banggusuk_michelin.Repository.UserRepository;
import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.controller.GroupController;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.RestaurantCreationDto;
import com.example.banggusuk_michelin.entity.Restaurant;
import com.example.banggusuk_michelin.entity.RestaurantComment;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.service.GroupService;
import com.example.banggusuk_michelin.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class restaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RestaurantCommentRepository restaurantCommentRepository;
    

    @MockCustomUser
    @Transactional
    @Test
    void createTest() throws Exception {

        //1. 유저 생성
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User)authentication.getPrincipal();

        userRepository.save(principal);

        //2. 그룹 생성
        GroupCreationDto groupCreationDto = new GroupCreationDto();
        groupCreationDto.setGroupName("testGroup");
        groupCreationDto.setPassword("1234");

        Map<String, Object> group = groupService.createGroup(groupCreationDto, principal);

        //3. 식당 생성
        RestaurantCreationDto dto = new RestaurantCreationDto();
        dto.setRestaurantName("testRestaurant");
        dto.setAddress("testAddress");
        dto.setRating(3);
        dto.setComment("delicious");
        dto.setGroupId(group.get("groupId").toString());

        Map<String, Object> restaurant = restaurantService.createRestaurant(dto, principal);

        //4. 데이터 검증
        int restaurantId = (int) restaurant.get("restaurantId");

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId);
        assertThat(findRestaurant.getRestaurantName()).isEqualTo(dto.getRestaurantName());
    }
}
