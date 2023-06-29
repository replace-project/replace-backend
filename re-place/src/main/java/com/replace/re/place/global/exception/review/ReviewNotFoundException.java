package com.replace.re.place.global.exception.review;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewNotFoundException extends CustomException {

    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
