package com.replace.re.place.review.service;

import com.replace.re.place.category.dto.CategoryDto;
import com.replace.re.place.category.service.CategoryService;
import com.replace.re.place.image.service.ImageService;
import com.replace.re.place.review.dto.GetDetailedReviewDto;
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
    public Long createReview(PostReviewDto postReviewDto) throws IOException {
        ReviewDto reviewDto = postReviewDto.getReviewDto();

        // 리뷰 생성
        Long reviewId = reviewService.insertReview(reviewDto);

        //이미지 저장
        List<MultipartFile> multipartFiles = postReviewDto.getMultipartFiles();
        imageService.insertReviewImage(reviewId, multipartFiles);

        //카테고리 저장
        List<String> categoryNames = postReviewDto.getCategoryNames();
        categoryService.insertReviewCategory(reviewId, categoryNames);

        // 공간 저장
        // ...

        return reviewId;
    }


    @Transactional
    public void deleteReview(Long reviewId){

        // 이미지 삭제
        imageService.deleteReviewImage(reviewId);

        // 카테고리 삭제
        categoryService.deleteReviewCategory(reviewId);

        //공간 삭제
        // ...

        // 리뷰 삭제
        reviewService.deleteReview(reviewId);
    }



    public GetDetailedReviewDto getReview(Long reviewId){

        // 리뷰 조회
        ReviewDto reviewDto = reviewService.getReview(reviewId);

        // 이미지 조회
        List<String> reviewImages = imageService.getReviewImages(reviewId);

        // 카테고리 조회
        List<CategoryDto> categoryDtos = categoryService.getReviewCategories(reviewId);

        //공간 조회
        // ...

        GetDetailedReviewDto getDetailedReviewDto = GetDetailedReviewDto.builder()
                .reviewDto(reviewDto)
                .imageUrls(reviewImages)
                .categoryDtos(categoryDtos).build();


        return getDetailedReviewDto;
    }
}
