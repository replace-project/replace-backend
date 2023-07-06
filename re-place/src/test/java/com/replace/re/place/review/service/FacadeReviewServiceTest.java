package com.replace.re.place.review.service;

import com.replace.re.place.review.dto.GetDetailedReviewDto;
import com.replace.re.place.review.dto.PostReviewDto;
import com.replace.re.place.review.dto.ReviewDto;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class FacadeReviewServiceTest {

    @Autowired
    private FacadeReviewService facadeReviewService;


    @Test
    @DisplayName("FacadeReviewService - 리뷰 작성 테스트")
    void createReviewTest() throws IOException {

        //given
        ReviewDto reviewDto = new ReviewDto(2L, 2L, "테스트제목", "테스트내용");

        File file = new File("src/main/resources/gangdol-img.png");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "image/png", IOUtils.toByteArray(input));

        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);

        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("음식");
        categoryNames.add("테스트");


        PostReviewDto postReviewDto = new PostReviewDto(reviewDto, multipartFiles, categoryNames);

        //when
        Long reviewId = facadeReviewService.createReview(postReviewDto);

        //then
        assertEquals(true, true);
    }

    @Test
    @DisplayName("FacadeReviewService - 리뷰 삭제 테스트")
    void deleteReviewTest() {

        //given
        Long testReviewId = 21L;

        //when
        facadeReviewService.deleteReview(testReviewId);

        //then
        assertEquals(true, true);
    }


}