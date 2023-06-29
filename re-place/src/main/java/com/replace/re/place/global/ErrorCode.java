package com.replace.re.place.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum ErrorCode {

    REVIEW_NOT_FOUND("U-200", "해당 리뷰가 존재하지 않습니다."),
    EXAMPLE_EXCEPTION("U-200", "임시 예외");

    private String code;
    private String message;


    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}
