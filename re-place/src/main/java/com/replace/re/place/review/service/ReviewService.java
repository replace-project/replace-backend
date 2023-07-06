package com.replace.re.place.review.service;


import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.review.ReviewNotCreatedException;
import com.replace.re.place.review.dao.ReviewDao;
import com.replace.re.place.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.replace.re.place.global.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDao reviewDao;

    // 리뷰를 생성하고, 생성된 pk가 유효한지 검사.
    public Long insertReview(ReviewDto reviewDto){
        Long reviewId = reviewDao.insertReview(reviewDto);

        Boolean isCreated = reviewDao.checkReviewExist(reviewId);
        if(!isCreated){
            throw new ReviewNotCreatedException(REVIEW_NOT_CREATEED);
        }

        return reviewId;
    }
}
