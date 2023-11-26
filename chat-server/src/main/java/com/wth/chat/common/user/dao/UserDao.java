package com.wth.chat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.mapper.UserMapper;
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


    public boolean modifyName(Long uid, String name) {
        return lambdaUpdate()
                .set(User::getName, name)
                .eq(User::getId, uid)
                .update();
    }

    public User getByName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }

    public boolean wearingBadge(Long uid, Long itemId) {
        return lambdaUpdate()
                .set(User::getItemId, itemId)
                .eq(User::getId, uid)
                .update();
    }
}
