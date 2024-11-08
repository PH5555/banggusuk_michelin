package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupVerificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;

    public GroupVerificationDto verifyGroupName(String groupName){
        if(groupName.length() > 30){
            return new GroupVerificationDto(false, "그룹 이름은 30자를 넘으면 안됩니다.");
        }

        if(groupRepository.findByGroupName(groupName).isPresent()){
            return new GroupVerificationDto(false, "그룹 이름이 중복됩니다.");
        }

        return new GroupVerificationDto(true, "검증 완료");
    }

    public Map<String, String> createGroup(GroupCreationDto groupCreationDto){
        if(!verifyGroupName(groupCreationDto.getGroupName()).getStatus()){
            return Map.of();
        }

        // 파일 url로 변경

        String password = groupCreationDto.getPassword();
        String hashedPassword = passwordEncoder.encode(password);



    }
}
