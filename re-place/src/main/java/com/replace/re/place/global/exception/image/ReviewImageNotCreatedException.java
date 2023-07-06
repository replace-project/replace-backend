package com.replace.re.place.global.exception.image;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewImageNotCreatedException extends CustomException {
    public ReviewImageNotCreatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
