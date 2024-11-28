package com.example.banggusuk_michelin.service;

import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleStorageService {
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    private final Storage storage;

    public String uploadImage(MultipartFile file){
        String uuid = UUID.randomUUID().toString();
        String ext = file.getContentType();

        BlobId blobId = BlobId.of(bucketName, uuid);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(ext)
                .build();
        try (WriteChannel writer = storage.writer(blobInfo)) {
            byte[] imageData = file.getBytes(); // 이미지 데이터를 byte 배열로 읽어옵니다.
            writer.write(ByteBuffer.wrap(imageData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return uuid;
    }
}