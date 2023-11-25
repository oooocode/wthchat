package com.wth.chat.common.user.service.impl;


import com.wth.chat.common.common.exception.BusinessException;
import com.wth.chat.common.user.dao.UserBackpackDao;
import com.wth.chat.common.user.dao.UserDao;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.enums.ItemEnum;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import com.wth.chat.common.user.service.UserService;
import com.wth.chat.common.user.service.adapter.UserAdapter;
import com.wth.chat.common.websocket.service.WebSocketService;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.ByteChunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.wth.chat.common.user.service.impl.WXMsgServiceImpl.WAIT_AUTHORIZE_MAP;

/**
 * @author 29977
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-10-18 21:32:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserBackpackDao userBackpackDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long register(User user) {
        boolean save = userDao.save(user);
        // todo  通知其他订阅者，如赠送积分等等
        return user.getId();
    }

    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openid = userInfo.getOpenid();
        User user = userDao.getByOpenId(openid);
        if (StringUtils.isBlank(user.getAvatar())) {
            fillUserInfo(user.getId(), userInfo);
        }
        // 用户登录
        Integer code = WAIT_AUTHORIZE_MAP.remove(openid);
        webSocketService.scanLoginSuccess(code, user.getId());

    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameCount);
    }

    @Override
    public void modifyName(Long uid, String name) {
        User user = userDao.getByName(name);
        if (Objects.nonNull(user)) {
            throw new BusinessException("名字不能重复!");
        }
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        if (modifyNameCount <= 0) {
            throw new RuntimeException("您没有改名卡了!");
        }
        userDao.modifyName(uid, name);
    }

    private void fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildAuthorizeUser(uid, userInfo);
        user.setName(userInfo.getNickname());
        user.setAvatar(userInfo.getHeadImgUrl());
        userDao.updateById(user);
    }
}




