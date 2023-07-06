package com.replace.re.place.global.exception.review;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewNotDeletedException extends CustomException {
    public ReviewNotDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
