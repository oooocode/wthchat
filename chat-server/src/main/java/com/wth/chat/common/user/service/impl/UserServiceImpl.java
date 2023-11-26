package com.wth.chat.common.user.service.impl;


import com.wth.chat.common.common.annotation.RedissonLock;
import com.wth.chat.common.common.config.CacheConfig;
import com.wth.chat.common.common.utils.AssertUtil;
import com.wth.chat.common.user.dao.ItemConfigDao;
import com.wth.chat.common.user.dao.UserBackpackDao;
import com.wth.chat.common.user.dao.UserDao;
import com.wth.chat.common.user.domain.entity.ItemConfig;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.entity.UserBackpack;
import com.wth.chat.common.user.domain.enums.ItemEnum;
import com.wth.chat.common.user.domain.enums.ItemTypeEnum;
import com.wth.chat.common.user.domain.vo.resp.BadgeResp;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import com.wth.chat.common.user.service.UserService;
import com.wth.chat.common.user.service.adapter.UserAdapter;
import com.wth.chat.common.user.service.cache.ItemCache;
import com.wth.chat.common.websocket.service.WebSocketService;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ItemConfigDao itemConfigDao;

    @Autowired
    private ItemCache itemCache;

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
    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(key = "#uid")
    public void modifyName(Long uid, String name) {
        User oldUser = userDao.getByName(name);
        AssertUtil.isEmpty(oldUser, "名字不能重复!");
        UserBackpack userBackpack = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtil.isNotEmpty(userBackpack, "改名卡已经没有了!");
        boolean success = userBackpackDao.useItem(userBackpack);
        if (success) {
            userDao.modifyName(uid, name);
        }
    }

    @Override
    public List<BadgeResp> badges(Long uid) {
        // 查询所有徽章
        List<ItemConfig> itemList = itemCache.getByType(ItemTypeEnum.BADGE.getType());
        // 查询用户拥有的徽章
        List<Long> itemIds = itemList.stream().map(ItemConfig::getId).collect(Collectors.toList());
        List<UserBackpack> userItems = userBackpackDao.getByItemIds(uid, itemIds);
        // 查询用户是否佩戴
        User user = userDao.getById(uid);
        return UserAdapter.buildBadge(itemList, userItems, user);
    }

    @Override
    public void wearingBadge(Long uid, Long itemId) {
        // 查询徽章
        UserBackpack userBackpack = userBackpackDao.getFirstValidItem(uid, itemId);
        AssertUtil.isNotEmpty(userBackpack, "您还没有这个徽章哦，快去获取吧");
        ItemConfig itemConfig = itemConfigDao.getById(itemId);
        AssertUtil.equal(itemConfig.getType(), ItemTypeEnum.BADGE.getType(), "必须是徽章才可以穿戴哦");
        userDao.wearingBadge(uid, itemId);
    }

    private void fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildAuthorizeUser(uid, userInfo);
        user.setName(userInfo.getNickname());
        user.setAvatar(userInfo.getHeadImgUrl());
        userDao.updateById(user);
    }
}




