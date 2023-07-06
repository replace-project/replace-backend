package com.replace.re.place.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum ErrorCode {

    REVIEW_NOT_FOUND("U-200", "해당 리뷰가 존재하지 않습니다."),
    REVIEW_NOT_CREATED("U-XXX", "리뷰 생성 오류."),
    REVIEW_NOT_DELETED("U-XXX", "리뷰 삭제 오류."),


    IMAGE_NOT_CREATED("U-XXX", "이미지 생성 오류."),
    IMAGE_NOT_FOUND("U-XXX", "해당 이미지가 존재하지 않습니다."),
    IMAGE_NOT_DELETED("U-XXX", "이미지 삭제 오류"),


    REVIEW_IMAGE_NOT_CREATED("U-XXX", "리뷰 이미지 생성 오류."),
    REVIEW_IMAGE_NOT_FOUND("U-XXX", "해당 리뷰 이미지가 존재하지 않습니다."),
    REVIEW_IMAGE_NOT_DELETED("U-XXX", "리뷰 이미지 삭제 오류"),


    CATEGORY_NOT_CREATED("U-XXX", "카테고리 생성 오류."),
    CATEGORY_NOT_FOUND("U-XXX", "해당 카테고리가 존재하지 않습니다."),
    CATEGORY_NOT_DELETED("U-XXX", "카테고리 삭제 오류"),

    REVIEW_CATEGORY_NOT_CREATED("U-XXX", "리뷰 카테고리 생성 오류."),
    REVIEW_CATEGORY_NOT_FOUND("U-XXX", "해당 리뷰 카테고리가 존재하지 않습니다."),
    REVIEW_CATEGORY_NOT_DELETED("U-XXX", "리뷰 카테고리 삭제 오류"),

    EXAMPLE_EXCEPTION("U-200", "임시 예외");

    private String code;
    private String message;


    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}
