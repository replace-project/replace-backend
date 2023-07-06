package com.replace.re.place.global.exception.category;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class CategoryNotCreatedException extends CustomException {
    public CategoryNotCreatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
