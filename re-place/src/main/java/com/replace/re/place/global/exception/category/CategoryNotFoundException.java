package com.replace.re.place.global.exception.category;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class CategoryNotFoundException extends CustomException {
    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
