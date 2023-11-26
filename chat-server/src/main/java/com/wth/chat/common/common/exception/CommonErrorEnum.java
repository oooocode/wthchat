package com.wth.chat.common.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 23:17
 */
@Getter
@AllArgsConstructor
public enum CommonErrorEnum implements ErrorEnum {
    BUSINESS_ERROR(0, "{0}"),
    SYSTEM_ERROR(-1, "系统出小差了, 请稍后再试"),
    PARAM_INVALID(-2, "参数校验错误!"),
    LOCK_LIMIT(-3, "调用频繁!"),
    ;

    private final Integer code;
    private final String msg;
    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
