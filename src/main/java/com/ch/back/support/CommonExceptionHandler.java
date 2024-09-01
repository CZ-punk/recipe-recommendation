package com.ch.back.support;


import com.ch.back.support.error.CoreApiException;
import com.ch.back.support.error.ErrorType;
import com.ch.back.support.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(CoreApiException.class)
    public ApiResponse<?> exceptionHandler(CoreApiException e) {
        log.error(e.getMessage());
        return ApiResponse.error(ErrorType.TOKEN_ERROR);
    }
}
