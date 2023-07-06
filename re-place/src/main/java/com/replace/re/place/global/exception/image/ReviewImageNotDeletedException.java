package com.replace.re.place.global.exception.image;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewImageNotDeletedException extends CustomException {
    public ReviewImageNotDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
