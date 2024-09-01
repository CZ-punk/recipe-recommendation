package com.ch.back.support.error;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error has occurred.",
            LogLevel.ERROR),

    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "bad request.", LogLevel.ERROR),

    TOKEN_ERROR(HttpStatus.UNAUTHORIZED, ErrorCode.E400, "Token Error.", LogLevel.ERROR),

    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, ErrorCode.E400, "Not found data.", LogLevel.ERROR),

    ROLE_ERROR(HttpStatus.UNAUTHORIZED, ErrorCode.E400, "Not valid role.", LogLevel.ERROR);

    private final HttpStatus status;

    private final ErrorCode code;

    private final String message;

    private final LogLevel logLevel;

    ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {

        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

}
