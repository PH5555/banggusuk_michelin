package com.example.banggusuk_michelin.group;

import com.example.banggusuk_michelin.MockCustomUser;
import com.example.banggusuk_michelin.Repository.UserRepository;
import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.controller.GroupController;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupJoinDto;
import com.example.banggusuk_michelin.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class GroupControllerTest {

    @Autowired
    private GroupController groupController;

    @Autowired
    private UserRepository userRepository;

    @Test
    @MockCustomUser
    @Transactional
    void test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User)authentication.getPrincipal();

        userRepository.save(principal);

        MockMultipartFile file = null;
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

        GroupCreationDto groupCreationDto = new GroupCreationDto();
        groupCreationDto.setGroupName("test");
        groupCreationDto.setPassword("1234");
        ApiResponse<Map<String, Object>> result = groupController.createGroup(groupCreationDto, file, principal);
        Assertions.assertThat(result.getStatus()).isEqualTo("success");
    }
}
