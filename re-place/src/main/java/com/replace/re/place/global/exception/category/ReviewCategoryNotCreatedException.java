package com.replace.re.place.global.exception.category;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ReviewCategoryNotCreatedException extends CustomException {
    public ReviewCategoryNotCreatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
