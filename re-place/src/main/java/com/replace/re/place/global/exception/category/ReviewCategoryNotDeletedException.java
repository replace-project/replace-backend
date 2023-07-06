package com.replace.re.place.global.exception.category;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewCategoryNotDeletedException extends CustomException {
    public ReviewCategoryNotDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}