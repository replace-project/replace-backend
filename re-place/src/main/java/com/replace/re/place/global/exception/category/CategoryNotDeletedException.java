package com.replace.re.place.global.exception.category;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class CategoryNotDeletedException extends CustomException {
    public CategoryNotDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
