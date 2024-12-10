package com.example.banggusuk_michelin.group;

import com.example.banggusuk_michelin.dto.GroupCreationDto;
import com.example.banggusuk_michelin.service.GoogleStorageService;
import com.example.banggusuk_michelin.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
@Slf4j
public class FileTest {

    @Autowired
    private GoogleStorageService googleStorageService;

    //TODO: 환경변수 넣고 다시 테스트
    @Test
    void fileUploadTest(){

        MockMultipartFile file = null;
        try {
            file = new MockMultipartFile(
                    "테스트 이미지",
                    "pika.png",
                    MediaType.IMAGE_PNG_VALUE,
                    new FileInputStream(new File("/Users/kimdonghyeon/pika.png"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GroupCreationDto dto = new GroupCreationDto();
        dto.setGroupName("dong");
        dto.setPassword("1234");
        dto.setGroupImage(file);

        String url = googleStorageService.uploadImage(dto.getGroupImage());
        log.info(url);
    }
}
