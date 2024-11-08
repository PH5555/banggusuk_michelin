package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupVerificationDto;
import com.example.banggusuk_michelin.entity.Group;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    private final Storage storage;
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

    public String uploadImage(GroupCreationDto groupCreationDto){
        String uuid = UUID.randomUUID().toString();
        String ext = groupCreationDto.getGroupImage().getContentType();

        BlobId blobId = BlobId.of(bucketName, uuid);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(ext)
                .build();
        try (WriteChannel writer = storage.writer(blobInfo)) {
            byte[] imageData = groupCreationDto.getGroupImage().getBytes(); // 이미지 데이터를 byte 배열로 읽어옵니다.
            writer.write(ByteBuffer.wrap(imageData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return uuid;
    }

    public Map<String, String> createGroup(GroupCreationDto groupCreationDto){
        String imageUrl = "";
        if(!verifyGroupName(groupCreationDto.getGroupName()).getStatus()){
            return Map.of();
        }

        if(!groupCreationDto.getGroupImage().isEmpty()){
            imageUrl = uploadImage(groupCreationDto);
        }
        String password = groupCreationDto.getPassword();
        String hashedPassword = passwordEncoder.encode(password);

        Group group = new Group();
        group.setGroupName(groupCreationDto.getGroupName());
        group.setPassword(hashedPassword);

        if(imageUrl.isEmpty()){
            group.setImage(imageUrl);
        }

        Group savedGroup = groupRepository.save(group);
        return Map.of("groupId", savedGroup.getGroupId());
    }
}
