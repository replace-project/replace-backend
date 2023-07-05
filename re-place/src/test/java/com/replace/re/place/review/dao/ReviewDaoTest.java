package com.replace.re.place.review.dao;

import com.replace.re.place.review.dto.ReviewDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewDaoTest {

    @Autowired
    private ReviewDao reviewDao;

    @Test
    void selectByStoreIdTest() {

        List<ReviewDto> reviewDtos = reviewDao.selectByStoreId(2L);
        assertEquals(2, reviewDtos.size());
    }

    @Test
    void selectByReviewIdTest() {
        ReviewDto reviewDto = reviewDao.selectByReviewId(2L);
        assertEquals("스프링", reviewDto.getTitle());
    }

    @Test
    @Transactional
    void insertReviewTest() {
        ReviewDto reviewDto = new ReviewDto(3L, 1L, "테스트제목", "테스트내용");
        Long reviewId = reviewDao.insertReview(reviewDto);

        assertEquals(
                reviewDao.selectByReviewId(reviewId).getTitle(), reviewDto.getTitle()
        );
    }

    @Test
    @Transactional
    void deleteReviewTest() {
        ReviewDto reviewDto = new ReviewDto(3L, 1L, "테스트제목", "테스트내용");
        Long reviewId = reviewDao.insertReview(reviewDto);

        reviewDao.deleteReview(reviewId);

        assertEquals(reviewDao.checkReviewExist(reviewId), false);
    }

    @Test
    @Transactional
    void updateReviewTest() {
        ReviewDto reviewDto = new ReviewDto(3L, 1L, "테스트제목", "테스트내용");
        Long reviewId = reviewDao.insertReview(reviewDto);

        reviewDto.setReviewId(reviewId);
        reviewDto.setTitle("테스트제목2");

        reviewDao.updateReview(reviewDto);

        ReviewDto updatedReviewDto = reviewDao.selectByReviewId(reviewId);

        assertEquals("테스트제목2", updatedReviewDto.getTitle());
    }
}