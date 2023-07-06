package com.replace.re.place.global.exception.image;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewImageNotFoundException extends CustomException {
    public ReviewImageNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
