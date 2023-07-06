package com.replace.re.place.global.exception.review;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;


public class ReviewNotCreatedException extends CustomException {
    public ReviewNotCreatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
