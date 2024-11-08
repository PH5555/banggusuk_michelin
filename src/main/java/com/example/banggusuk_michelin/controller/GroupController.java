package com.example.banggusuk_michelin.controller;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ApiResponse<Map<String, String>> createGroup(@RequestBody GroupCreationDto groupCreationDto){
        // 중복 확인
        // 비밀번호 생성
        groupService.createGroup(groupCreationDto);
        Map<String, String> data = new HashMap<>();
        // 데이터 input
        return ApiResponse.success(data);
    }
}
