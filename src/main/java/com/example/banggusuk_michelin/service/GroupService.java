package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.Repository.UserGroupRepository;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupJoinDto;
import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.entity.UserGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;
    private final GoogleStorageService googleStorageService;

    public Map<String, String> verifyGroupName(String groupName){
        if(groupName.length() > 30){
            throw new IllegalArgumentException("그룹 이름은 30자를 넘으면 안됩니다.");
        }

        if(groupRepository.findByGroupName(groupName).isPresent()){
            throw new IllegalArgumentException("그룹 이름이 중복됩니다.");
        }

        return Map.of("message", "검증완료");
    }

    @Transactional
    public Map<String, Object> createGroup(GroupCreationDto groupCreationDto, User user){
        try{
            verifyGroupName(groupCreationDto.getGroupName());
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }

        String password = groupCreationDto.getPassword();
        String hashedPassword = passwordEncoder.encode(password);

        Group group = new Group();
        group.setGroupName(groupCreationDto.getGroupName());
        group.setPassword(hashedPassword);

        if(groupCreationDto.getGroupImage() != null){
            group.setImage(googleStorageService.uploadImage(groupCreationDto.getGroupImage()));
        }

        Group savedGroup = groupRepository.save(group);
        UserGroup userGroup = userGroupRepository.save(user, savedGroup);

        return Map.of("groupId", savedGroup.getGroupId(), "user_group_id", userGroup.getId());
    }

    @Transactional
    public Map<String, Object> joinGroup(GroupJoinDto groupJoinDto, User user) throws Exception {
        Optional<Group> group = groupRepository.findByGroupName(groupJoinDto.getGroupName());

        if(group.isEmpty()){
            throw new Exception("존재하지 않는 그룹입니다.");
        }

        if(userGroupRepository.findGroupInUser(user, group.get())){
            throw new Exception("이미 가입한 그룹입니다.");
        }

        if(!passwordEncoder.matches(groupJoinDto.getPassword(), group.get().getPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        UserGroup userGroup = userGroupRepository.save(user, group.get());
        return Map.of("group_id", group.get().getGroupId(), "user_group_id", userGroup.getId());
    }
}
