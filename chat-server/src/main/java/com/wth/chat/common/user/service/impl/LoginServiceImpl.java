package com.wth.chat.common.user.service.impl;

import com.wth.chat.common.common.utils.RedisUtils;
import com.wth.chat.common.constants.RedisKey;
import com.wth.chat.common.user.service.LoginService;
import com.wth.chat.common.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wth
 * @Create: 2023/11/2 - 0:17
 */
@Service
public class LoginServiceImpl implements LoginService {

    public static final int TOKEN_EXPIRE_DAYS = 3;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void renewalTokenIfNecessary(String token) {

    }

    @Override
    public Long getValidUid(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        Long oldToken = RedisUtils.get(getUserTokenKey(uid), Long.class);
        if (Objects.isNull(oldToken)) {
            return null;
        }
        return uid;
    }

    private static String getUserTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        RedisUtils.set(getUserTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }
}
