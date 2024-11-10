package com.example.banggusuk_michelin.group;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.controller.GroupController;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Slf4j
public class GroupControllerTest {

    @Autowired
    private GroupController groupController;

    @Test
    void repositoryTest(){
        GroupCreationDto dto = new GroupCreationDto();
        dto.setGroupName("dong");
        dto.setPassword("1234");
        ApiResponse<Map<String, String>> result = groupController.createGroup(dto);
        log.info(result.toString());
    }
}
