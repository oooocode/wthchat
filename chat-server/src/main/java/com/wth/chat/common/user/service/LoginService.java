package com.wth.chat.common.user.service;

/**
 * @Author: wth
 * @Create: 2023/11/2 - 0:17
 */
public interface LoginService {


    /**
     * 刷新token有效期
     *
     * @param token
     */
    void renewalTokenIfNecessary(String token);

    /**
     * 如果token有效，返回uid
     *
     * @param token
     * @return
     */
    Long getValidUid(String token);

    String login(Long uid);
}
