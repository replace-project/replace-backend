package com.replace.re.place.image.service;


import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    @DisplayName("리뷰 이미지 INSERT 테스트")
    public void reviewImageInsertTest() throws IOException {

        //given
        File file = new File("src/main/resources/gangdol-img.png");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "image/png", IOUtils.toByteArray(input));

        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);

        //when
        Boolean isInserted = imageService.insertReviewImage(1L, multipartFiles);


        //then
        assertEquals(true, isInserted);
    }


    @Test
    @DisplayName("리뷰 이미지 DELETE 테스트")
    public void reviewImageDeleteTest() throws IOException {
        
        //given
        File file = new File("src/main/resources/gangdol-img.png");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "image/png", IOUtils.toByteArray(input));

        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);
        imageService.insertReviewImage(1L, multipartFiles);


        //when
        Boolean isDeleted = imageService.deleteReviewImage(1L);


        //then
        assertEquals(true, isDeleted);
    }
}