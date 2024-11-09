package com.example.banggusuk_michelin.group;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.entity.Group;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void saveTest() {
        Group group = new Group("dong", "1234", "test");
        Group savedGroup = groupRepository.save(group);
        assertThat(savedGroup).isEqualTo(group);
        log.info("group id : " + savedGroup.getGroupId());
    }

    @Test
    void findTest() {
        Group group = new Group("dong", "1234", "test");
        Group savedGroup = groupRepository.save(group);
        Optional<Group> findGroup = groupRepository.findByGroupName("dong");
        assertThat(savedGroup).isEqualTo(findGroup.get());

    }
}
