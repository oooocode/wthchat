package com.wth.chat.common.common.utils;

import com.wth.chat.common.user.domain.dto.RequestInfo;

/**
 * Description: 请求上下文
 * @Author: wth
 * @Create: 2023/11/25 - 18:26
 */
public class RequestHolder {

    public static final ThreadLocal<RequestInfo> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        THREAD_LOCAL.set(requestInfo);
    }

    public static RequestInfo get() {
        return THREAD_LOCAL.get();
    }

    public static String getIp() {
        return THREAD_LOCAL.get().getIp();
    }

    public static Long getUid() {
        return THREAD_LOCAL.get().getUid();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
