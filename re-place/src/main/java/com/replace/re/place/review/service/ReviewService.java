package com.replace.re.place.review.service;


import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.review.ReviewNotCreatedException;
import com.replace.re.place.global.exception.review.ReviewNotDeletedException;
import com.replace.re.place.global.exception.review.ReviewNotFoundException;
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
            throw new ReviewNotCreatedException(REVIEW_NOT_CREATED);
        }

        return reviewId;
    }


    // 삭제할 리뷰가 존재하는지 먼저 검사하고, 삭제 후 정말 삭제되었는지 확인.
    public void deleteReview(Long reviewId){
        Boolean isReviewExist = reviewDao.checkReviewExist(reviewId);

        if(isReviewExist) {
            reviewDao.deleteReview(reviewId);

            Boolean isReviewDeleted = !reviewDao.checkReviewExist(reviewId);
            if (!isReviewDeleted) {
                throw new ReviewNotDeletedException(REVIEW_NOT_DELETED);
            }


        }else {
            throw new ReviewNotFoundException(REVIEW_NOT_FOUND);
        }
    }


    public ReviewDto getReview(Long reviewId){
        Boolean isReviewExist = reviewDao.checkReviewExist(reviewId);

        if(isReviewExist){
            ReviewDto reviewDto = reviewDao.selectByReviewId(reviewId);

            return reviewDto;
        }
        throw new ReviewNotFoundException(REVIEW_NOT_FOUND);
    }
}
