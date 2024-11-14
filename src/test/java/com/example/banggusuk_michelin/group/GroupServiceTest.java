package com.example.banggusuk_michelin.group;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupJoinDto;
import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

//    @Test
//    void createGroupTest(){
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//
//        Map<String, String> group = groupService.createGroup(dto);
//        Optional<Group> findGroup = groupRepository.findGroupById(group.get("groupId"));
//        assertThat(findGroup.get().getGroupName()).isEqualTo(dto.getGroupName());
//
//    }

//    @Test
//    void verifyTest(){
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//
//        Map<String, String> group = groupService.createGroup(dto);
//
//        GroupCreationDto dto2 = new GroupCreationDto();
//        dto2.setGroupName("dong");
//        dto2.setPassword("1234");
//    }

//    @Test
//    void joinTest(){
//        GroupCreationDto dto = new GroupCreationDto();
//        dto.setGroupName("dong");
//        dto.setPassword("1234");
//
//        Map<String, String> group = groupService.createGroup(dto);
//
//        GroupJoinDto groupJoinDto = new GroupJoinDto();
//        groupJoinDto.setGroupName("dong");
//        groupJoinDto.setPassword("1234");
//        try {
//            Map<String, String> result = groupService.joinGroup(groupJoinDto);
//            String id = result.get("groupId");
//            assertThat(id).isEqualTo(group.get("groupId"));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
