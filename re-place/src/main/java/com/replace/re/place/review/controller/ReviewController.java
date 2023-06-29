package com.replace.re.place.review.controller;


import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.ErrorResponse;
import com.replace.re.place.global.exception.review.ReviewNotFoundException;
import com.replace.re.place.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping
    public ResponseEntity<Object> exceptionTest(){

        throw new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> reviewExceptionHandler1(ReviewNotFoundException e){
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
