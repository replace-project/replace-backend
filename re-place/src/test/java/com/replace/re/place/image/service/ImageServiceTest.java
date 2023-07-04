package com.replace.re.place.image.service;

import com.replace.re.place.image.dto.ImageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ImageServiceTest {

    @Autowired
    private ImageService imageService;


    @Test
    @DisplayName("리뷰 이미지 INSERT 테스트")
    @Transactional
    public void reviewImageInsertTest(){

        List<String> imageUrls = new ArrayList<>();

        imageUrls.add("https://via.placeholder.com/300x200.png?text=Sample+Image1");
        imageUrls.add("https://via.placeholder.com/300x200.png?text=Sample+Image2");
        imageUrls.add("https://via.placeholder.com/300x200.png?text=Sample+Image3");

        Boolean isSuccess = imageService.insertReviewImage(1L, imageUrls);

        List<ImageDto> imageDtos = imageService.getReviewImagesByReviewId(1L);

        List<String> imageDtosUrls = new ArrayList<>();

        Iterator<ImageDto> it = imageDtos.iterator();
        while(it.hasNext()){
            ImageDto imageDto = it.next();

            imageDtosUrls.add(imageDto.getUrl());
        }

        Collections.sort(imageDtosUrls);

        assertEquals(imageUrls, imageDtosUrls);
    }


    @Test
    @DisplayName("리뷰 이미지 DELETE 테스트")
    @Transactional
    public void reviewImageDeleteTest(){

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://via.placeholder.com/300x200.png?text=Sample+Image1");
        imageUrls.add("https://via.placeholder.com/300x200.png?text=Sample+Image2");
        imageUrls.add("https://via.placeholder.com/300x200.png?text=Sample+Image3");

        Boolean isSuccess = imageService.insertReviewImage(1L, imageUrls);

        imageService.deleteReviewImage(1L);

        List<ImageDto> imageDtos = imageService.getReviewImagesByReviewId(100L);

        assertEquals(imageDtos.size(), 0);

    }
}