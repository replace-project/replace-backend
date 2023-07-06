package com.replace.re.place.global.exception.category;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewCategoryNotFoundException extends CustomException {
    public ReviewCategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
