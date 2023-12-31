package com.wth.chat.common.constants;

/**
 * @Author: wth
 * @Create: 2023/11/22 - 22:56
 */
public class RedisKey {

    public static final String BASE_KEY = "mallchat:chat:";

    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    /**
     * 用户的信息更新时间
     */
    public static final String USER_MODIFY_STRING = "userModify:uid_%d";

    /**
     * 用户信息
     */
    public static final String USER_INFO_STRING = "userInfo:uid_%d";

    /**
     * 用户的信息汇总
     */
    public static final String USER_SUMMARY_STRING = "userSummary:uid_%d";

    public static final String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
