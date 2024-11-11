package com.example.banggusuk_michelin.controller;

import com.example.banggusuk_michelin.apiFormat.ApiResponse;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupJoinDto;
import com.example.banggusuk_michelin.entity.User;
import com.example.banggusuk_michelin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ApiResponse<Map<String, String>> createGroup(GroupCreationDto groupCreationDto){
        Map<String, String> data = groupService.createGroup(groupCreationDto);
        return ApiResponse.success(data);
    }

    @GetMapping("/verify")
    public ApiResponse<Map<String, String>> verifyGroupName(String groupName){
        try{
            return ApiResponse.success(groupService.verifyGroupName(groupName));
        } catch (Exception e){
            return ApiResponse.fail(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/join")
    public ApiResponse<Map<String, String>> joinGroup(GroupJoinDto groupJoinDto
//            , @AuthenticationPrincipal User user
    ){
        try {
            return ApiResponse.success(groupService.joinGroup(groupJoinDto));
        } catch (Exception e) {
            return ApiResponse.fail(Map.of("message", e.getMessage()));
        }

    }
}
