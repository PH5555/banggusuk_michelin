package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.Repository.GroupRepository;
import com.example.banggusuk_michelin.Repository.UserGroupRepository;
import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.dto.GroupJoinDto;
import com.example.banggusuk_michelin.entity.Group;
import com.example.banggusuk_michelin.entity.User;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    private final Storage storage;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;

    public Map<String, String> verifyGroupName(String groupName){
        if(groupName.length() > 30){
            throw new IllegalArgumentException("그룹 이름은 30자를 넘으면 안됩니다.");
        }

        if(groupRepository.findByGroupName(groupName).isPresent()){
            throw new IllegalArgumentException("그룹 이름이 중복됩니다.");
        }

        return Map.of("message", "검증완료");
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

    @Transactional
    public Map<String, String> createGroup(GroupCreationDto groupCreationDto){
        try{
            verifyGroupName(groupCreationDto.getGroupName());
        }catch (Exception e){
            return Map.of("message", e.getMessage());
        }

        String password = groupCreationDto.getPassword();
        String hashedPassword = passwordEncoder.encode(password);

        Group group = new Group();
        group.setGroupName(groupCreationDto.getGroupName());
        group.setPassword(hashedPassword);

        if(groupCreationDto.getGroupImage() != null){
            group.setImage(uploadImage(groupCreationDto));
        }

        Group savedGroup = groupRepository.save(group);

//        userGroupRepository.save(user, savedGroup);
        //TODO: UserGroup에 group과 userid저장
        return Map.of("groupId", savedGroup.getGroupId());
    }

    @Transactional
    public Map<String, String> joinGroup(GroupJoinDto groupJoinDto) throws Exception {
        Optional<Group> group = groupRepository.findByGroupName(groupJoinDto.getGroupName());

        if(group.isEmpty()){
            throw new Exception("존재하지 않는 그룹입니다.");
        }

//        if(userGroupRepository.findGroupInUser(user, group)){
//            throw new Exception("이미 가입한 그룹입니다.");
//        }

        if(!passwordEncoder.matches(groupJoinDto.getPassword(), group.get().getPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

//      userGroupRepository.save(user, group);
        //TODO: UserGroup에 group과 userid저장

        return Map.of("groupId", group.get().getGroupId());
    }
}
