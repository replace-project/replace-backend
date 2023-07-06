package com.replace.re.place.global.exception.image;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ImageNotFoundException extends CustomException {
    public ImageNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
