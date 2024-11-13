package com.example.banggusuk_michelin.userGroup;


import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.Repository.UserGroupRepository;
import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.entity.UserGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserGroupRepositoryTest {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    GroupRepository groupRepository;

    @Test
    @Transactional
    void saveTest(){
        Group group = new Group("dong", "1234", "test");
        Group savedGroup = groupRepository.save(group);
        User user = new User();
        user.setNickname("김동현");
        UserGroup userGroup = userGroupRepository.save(user, savedGroup);
        Assertions.assertThat(savedGroup.getGroupId()).isEqualTo(userGroup.getGroup().getGroupId());
    }
}
