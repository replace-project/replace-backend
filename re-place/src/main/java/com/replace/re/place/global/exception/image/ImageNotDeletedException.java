package com.replace.re.place.global.exception.image;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ImageNotDeletedException extends CustomException {
    public ImageNotDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
