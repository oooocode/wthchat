package com.wth.chat.common.common.interceptor;

import cn.hutool.http.ContentType;
import com.google.common.base.Charsets;
import com.wth.chat.common.common.exception.HttpErrorEnum;
import com.wth.chat.common.common.utils.JsonUtils;
import com.wth.chat.common.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 17:57
 */
@Component
@Order(0)
public class TokenInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_SCHEME = "Bearer ";
    public static final String ATTRIBUTE_UID = "uid";
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        Long uid = loginService.getValidUid(token);
        boolean isPublic = isPublicURI(request);
        if (Objects.nonNull(uid)) {
            request.setAttribute(ATTRIBUTE_UID, uid);
        } else {
            if (!isPublic) {
                HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
                return false;
            }
        }
        return true;
    }

    private boolean isPublicURI(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String[] split = requestUri.split("/");
        return split.length > 2 && "public".equals(split[3]);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        return Optional.of(header)
                .filter(item -> item.startsWith(AUTHORIZATION_SCHEME))
                .map(item -> item.substring(AUTHORIZATION_SCHEME.length()))
                .orElse(null);
    }

}
