package com.ch.back.support.error;

import lombok.Getter;

@Getter
public class CoreApiException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public CoreApiException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public CoreApiException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

}
