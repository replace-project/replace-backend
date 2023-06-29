package com.replace.re.place.global;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private String code;
    private String message;
//    private String exceptionDesc;

    public ErrorResponse(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    // 정적 팩토리 메서드
    public static ErrorResponse of(ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }

}
