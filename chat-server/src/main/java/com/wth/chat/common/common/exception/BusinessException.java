package com.wth.chat.common.common.exception;

import lombok.Getter;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 23:35
 */
@Getter
public class BusinessException extends RuntimeException{

    protected Integer errorCode;
    protected String errorMsg;

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorCode = CommonErrorEnum.BUSINESS_ERROR.getErrorCode();
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
