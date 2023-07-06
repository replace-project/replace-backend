package com.replace.re.place.global.exception.image;

import com.replace.re.place.global.ErrorCode;
import com.replace.re.place.global.exception.CustomException;

public class ImageNotCreatedException extends CustomException {
    public ImageNotCreatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
