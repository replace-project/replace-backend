package com.replace.re.place.review.service;

import com.replace.re.place.category.service.CategoryService;
import com.replace.re.place.image.service.ImageService;
import com.replace.re.place.review.dto.PostReviewDto;
import com.replace.re.place.review.dto.ReviewDto;
import com.replace.re.place.store.service.StoreSercvice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacadeReviewService {

    private final ReviewService reviewService;
    private final ImageService imageService;
    private final StoreSercvice storeSercvice;
    private final CategoryService categoryService;


    @Transactional
    public void facade(PostReviewDto postReviewDto) throws IOException {
        ReviewDto reviewDto = postReviewDto.getReviewDto();
        // 리뷰 생성 로직 추가
        Long reviewId = reviewService.insertReview(reviewDto);


        //이미지 저장
        List<MultipartFile> multipartFiles = postReviewDto.getMultipartFiles();
        imageService.insertReviewImage(reviewId, multipartFiles);


        //카테고리 저장
        List<String> categoryNames = postReviewDto.getCategoryNames();
        categoryService.insertReviewCategory(reviewId, categoryNames);


        //공간 저장

    }
}
