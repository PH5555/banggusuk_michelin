package com.example.banggusuk_michelin.group;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.controller.GroupController;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupJoinDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class GroupControllerTest {

    @Autowired
    private GroupController groupController;

//    @Test
//    void repositoryTest() {
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//        ApiResponse<Map<String, String>> result = groupController.createGroup(dto);
//        log.info(result.toString());
//    }

//    @Test
//    void verifyTest() {
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//        groupController.createGroup(dto);
//
//        ApiResponse<Map<String, String>> result = groupController.verifyGroupName("dong");
//        assertThat(result.getStatus()).isEqualTo("fail");
//        log.info(result.toString());
//    }

//    @Test
//    void verifyTest2() {
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//        groupController.createGroup(dto);
//
//        ApiResponse<Map<String, String>> result = groupController.verifyGroupName("lee");
//        assertThat(result.getStatus()).isEqualTo("success");
//        log.info(result.toString());
//    }
//
//    @Test
//    void verifyTest3() {
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//        groupController.createGroup(dto);
//
//        ApiResponse<Map<String, String>> result = groupController.verifyGroupName("asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasd");
//        assertThat(result.getStatus()).isEqualTo("fail");
//        log.info(result.toString());
//    }

//    @Test
//    void joinTest1() {
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//
//        ApiResponse<Map<String, String>> group = groupController.createGroup(dto);
//
//        GroupJoinDto dto2 = new GroupJoinDto();
//        dto2.setGroupName("lee");
//        dto2.setPassword("1234");
//
//        ApiResponse<Map<String, String>> result = groupController.joinGroup(dto2);
//        assertThat(result.getStatus()).isEqualTo("fail");
//    }

//    @Test
//    void joinTest2() {
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//
//        ApiResponse<Map<String, String>> group = groupController.createGroup(dto);
//
//        GroupJoinDto dto2 = new GroupJoinDto();
//        dto2.setGroupName("dong");
//        dto2.setPassword("1222");
//
//        ApiResponse<Map<String, String>> result = groupController.joinGroup(dto2);
//        assertThat(result.getStatus()).isEqualTo("fail");
//        log.info(result.toString());
//    }

//    @Test
//    void joinTest3() {
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//
//        ApiResponse<Map<String, String>> group = groupController.createGroup(dto);
//
//        GroupJoinDto dto2 = new GroupJoinDto();
//        dto2.setGroupName("dong");
//        dto2.setPassword("1234");
//
//        ApiResponse<Map<String, String>> result = groupController.joinGroup(dto2);
//        assertThat(result.getStatus()).isEqualTo("success");
//        log.info(result.toString());
//    }

}
