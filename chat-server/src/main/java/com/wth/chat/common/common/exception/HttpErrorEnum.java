package com.wth.chat.common.common.exception;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Charsets;
import com.wth.chat.common.user.domain.vo.resp.ApiResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 18:11
 */
@AllArgsConstructor
@Getter
public enum HttpErrorEnum implements ErrorEnum {

    /**
     * 登录失效
     */
    ACCESS_DENIED(401, "登录失效，请重新登录"),
            ;
    private final Integer httpCode;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return httpCode;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(this.getErrorCode());
        ApiResult<?> responseData = ApiResult.fail(this);
        response.setContentType(ContentType.JSON.toString(Charsets.UTF_8));
        response.getWriter().write(JSONUtil.toJsonStr(responseData));
    }

}
