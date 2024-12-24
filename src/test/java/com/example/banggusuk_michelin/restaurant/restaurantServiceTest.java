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
import com.example.banggusuk_michelin.dto.RestaurantDto;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
        MockMultipartFile file = null;
        Map<String, Object> group = groupService.createGroup(groupCreationDto, file, principal);

        //3. 식당 생성
        try {
            file = new MockMultipartFile(
                    "테스트 이미지",
                    "pika.png",
                    MediaType.IMAGE_PNG_VALUE,
                    new FileInputStream(new File("/Users/kimdonghyeon/pika.png"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RestaurantCreationDto dto = new RestaurantCreationDto();
        dto.setRestaurantName("testRestaurant");
        dto.setAddress("testAddress");
        dto.setRating(3);
        dto.setComment("delicious");
        dto.setGroupId(group.get("groupId").toString());
        dto.setLongitude("17");
        dto.setLatitude("16");

        Map<String, Object> restaurant = restaurantService.createRestaurant(dto, null, principal);

        //4. 데이터 검증
        int restaurantId = (int) restaurant.get("restaurantId");

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId);
        assertThat(findRestaurant.getRestaurantName()).isEqualTo(dto.getRestaurantName());
    }

    @MockCustomUser
    @Transactional
    @Test
    void createDuplicateTest() throws Exception {

        //1. 유저 생성
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User)authentication.getPrincipal();

        userRepository.save(principal);
        MockMultipartFile file = null;
        //2. 그룹 생성
        GroupCreationDto groupCreationDto = new GroupCreationDto();
        groupCreationDto.setGroupName("testGroup");
        groupCreationDto.setPassword("1234");

        Map<String, Object> group = groupService.createGroup(groupCreationDto, file, principal);

        //3. 식당 생성
        RestaurantCreationDto dto = new RestaurantCreationDto();
        dto.setRestaurantName("testRestaurant");
        dto.setAddress("testAddress");
        dto.setRating(3);
        dto.setComment("delicious");
        dto.setGroupId(group.get("groupId").toString());

        Map<String, Object> restaurant = restaurantService.createRestaurant(dto, null, principal);

        //4. 같은 식당 생성
        RestaurantCreationDto dto2 = new RestaurantCreationDto();
        dto2.setRestaurantName("testRestaurant");
        dto2.setAddress("testAddress");
        dto2.setRating(2);
        dto2.setComment("delicious good!");
        dto2.setGroupId(group.get("groupId").toString());

        Map<String, Object> restaurant2 = restaurantService.createRestaurant(dto2, null, principal);

        //4. 데이터 검증
        assertThat(restaurant.get("restaurantId")).isEqualTo(restaurant2.get("restaurantId"));
    }

    @MockCustomUser
    @Transactional
    @Test
    void createWrongGroupTest(){

        //1. 유저 생성
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User)authentication.getPrincipal();

        userRepository.save(principal);

        //2. 그룹 생성
        GroupCreationDto groupCreationDto = new GroupCreationDto();
        groupCreationDto.setGroupName("testGroup");
        groupCreationDto.setPassword("1234");
        MockMultipartFile file = null;
        Map<String, Object> group = groupService.createGroup(groupCreationDto, file, principal);

        //3. 식당 생성
        RestaurantCreationDto dto = new RestaurantCreationDto();
        dto.setRestaurantName("testRestaurant");
        dto.setAddress("testAddress");
        dto.setRating(3);
        dto.setComment("delicious");
        dto.setGroupId(group.get("groupId").toString() + "1");

        //4. 데이터 검증
        Assertions.assertThatThrownBy(() -> restaurantService.createRestaurant(dto, null, principal)).isInstanceOf(Exception.class);
    }

    @MockCustomUser
    @Transactional
    @Test
    void searchTest() throws Exception {

        //1. 유저 생성
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User)authentication.getPrincipal();

        userRepository.save(principal);

        //2. 그룹 생성
        GroupCreationDto groupCreationDto = new GroupCreationDto();
        groupCreationDto.setGroupName("testGroup");
        groupCreationDto.setPassword("1234");
        MockMultipartFile file = null;
        Map<String, Object> group = groupService.createGroup(groupCreationDto, file, principal);

        //3. 식당 생성
        RestaurantCreationDto dto = new RestaurantCreationDto();
        dto.setRestaurantName("testRestaurant");
        dto.setAddress("testAddress");
        dto.setRating(3);
        dto.setComment("delicious");
        dto.setGroupId(group.get("groupId").toString());
        dto.setLatitude("16");
        dto.setLongitude("17");

        RestaurantCreationDto dto2 = new RestaurantCreationDto();
        dto2.setRestaurantName("testRestaurant");
        dto2.setAddress("testAddress");
        dto2.setRating(2);
        dto2.setComment("delicious");
        dto2.setGroupId(group.get("groupId").toString());
        dto2.setLatitude("16");
        dto2.setLongitude("17");

        RestaurantCreationDto dto3 = new RestaurantCreationDto();
        dto3.setRestaurantName("testRestaurant2");
        dto3.setAddress("testAddress2");
        dto3.setRating(1);
        dto3.setComment("delicious");
        dto3.setGroupId(group.get("groupId").toString());
        dto3.setLatitude("16");
        dto3.setLongitude("17");

        RestaurantCreationDto dto4 = new RestaurantCreationDto();
        dto4.setRestaurantName("testRestaurant3");
        dto4.setAddress("testAddress3");
        dto4.setRating(2);
        dto4.setComment("delicious");
        dto4.setGroupId(group.get("groupId").toString());
        dto4.setLatitude("16");
        dto4.setLongitude("17");

        restaurantService.createRestaurant(dto, null, principal);
        restaurantService.createRestaurant(dto2, null, principal);
        restaurantService.createRestaurant(dto3, null, principal);
        restaurantService.createRestaurant(dto4, null, principal);

        List<RestaurantDto> result = restaurantService.searchRestaurant(3, group.get("groupId").toString());
        log.info(result.toString());
        //4. 데이터 검증
        assertThat(result.size()).isEqualTo(0);
    }
}
