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
    public static final int TOKEN_RENEW_DAY = 1;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void renewalTokenIfNecessary(String token) {
        Long uid = getValidUid(token);
        // 无效的token,拒绝刷新
        if (Objects.isNull(uid)) {
            return;
        }
        String userTokenKey = getUserTokenKey(uid);
        Long expireDays = RedisUtils.getExpire(userTokenKey, TimeUnit.DAYS);
        // key 不存在返回-2
        if (expireDays == -2) {
            return;
        }
        // 小于1天刷新token
        if (expireDays < TOKEN_RENEW_DAY) {
            RedisUtils.expire(userTokenKey, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    @Override
    public Long getValidUid(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        // redis 的token过期uid将会失效
        String oldToken = RedisUtils.get(getUserTokenKey(uid));
        return Objects.equals(oldToken, token) ? uid : null;
    }

    private String getUserTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        RedisUtils.set(getUserTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }
}
