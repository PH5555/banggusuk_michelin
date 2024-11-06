package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.dto.GroupVerificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupVerificationDto verifyGroupName(String groupName){
        if(groupName.length() > 30){
            return new GroupVerificationDto(false, "그룹 이름은 30자를 넘으면 안됩니다.");
        }

        if(groupRepository.findByGroupName(groupName).isPresent()){
            return new GroupVerificationDto(false, "그룹 이름이 중복됩니다.");
        }

        return new GroupVerificationDto(true, "검증 완료");
    }

    public Map<String, String> createGroup(String groupName){
        if(!verifyGroupName(groupName).getStatus()){
            return Map.of();
        }

        //TODO: 비밀번호 생성
    }

    //비밀번호 만들기
}
