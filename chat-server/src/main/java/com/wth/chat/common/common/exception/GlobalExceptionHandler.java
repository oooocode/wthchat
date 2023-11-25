package com.wth.chat.common.common.exception;

import com.wth.chat.common.user.domain.vo.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 23:13
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> notValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(item -> errorMsg.append(item.getField())
                .append(item.getDefaultMessage())
                .append(","));
        String resultMsg = errorMsg.toString();
        return ApiResult.fail(CommonErrorEnum.PARAM_INVALID.getErrorCode(), resultMsg.substring(0, resultMsg.length() - 1));
    }

    @ExceptionHandler(value = BusinessException.class)
    public ApiResult<?> businessException(BusinessException e) {
        log.info("business exception, the reason is: {}", e.getMessage());
        return ApiResult.fail(e.getErrorCode(), e.getErrorMsg());
    }


    @ExceptionHandler(value = Throwable.class)
    public ApiResult<?> globalException(Throwable e) {
        log.error("system exception ! the reason is: {}", e.getMessage());
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }
}
