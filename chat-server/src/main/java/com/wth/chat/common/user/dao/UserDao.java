package com.wth.chat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.mapper.UserMapper;
import com.wth.chat.common.user.service.adapter.UserAdapter;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: wth
 * @Create: 2023/11/1 - 22:59
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByOpenId(String openId) {
        return lambdaQuery().eq(User::getOpenId, openId).one();
    }


}
