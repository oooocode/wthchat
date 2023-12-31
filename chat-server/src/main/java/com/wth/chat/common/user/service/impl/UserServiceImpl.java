package com.wth.chat.common.user.service.impl;


import com.wth.chat.common.common.annotation.RedissonLock;
import com.wth.chat.common.common.config.CacheConfig;
import com.wth.chat.common.common.event.UserBlackEvent;
import com.wth.chat.common.common.event.UserRegisterEvent;
import com.wth.chat.common.common.event.listener.RegisterEventListener;
import com.wth.chat.common.common.utils.AssertUtil;
import com.wth.chat.common.user.dao.BlackDao;
import com.wth.chat.common.user.dao.ItemConfigDao;
import com.wth.chat.common.user.dao.UserBackpackDao;
import com.wth.chat.common.user.dao.UserDao;
import com.wth.chat.common.user.domain.dto.ItemInfoDTO;
import com.wth.chat.common.user.domain.dto.SummeryInfoDTO;
import com.wth.chat.common.user.domain.entity.Black;
import com.wth.chat.common.user.domain.entity.ItemConfig;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.entity.UserBackpack;
import com.wth.chat.common.user.domain.enums.BlackTypeEnum;
import com.wth.chat.common.user.domain.enums.ItemEnum;
import com.wth.chat.common.user.domain.enums.ItemTypeEnum;
import com.wth.chat.common.user.domain.vo.req.ItemInfoReq;
import com.wth.chat.common.user.domain.vo.req.SummeryInfoReq;
import com.wth.chat.common.user.domain.vo.resp.BadgeResp;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import com.wth.chat.common.user.service.UserService;
import com.wth.chat.common.user.service.adapter.UserAdapter;
import com.wth.chat.common.user.service.cache.ItemCache;
import com.wth.chat.common.user.service.cache.UserCache;
import com.wth.chat.common.user.service.cache.UserSummaryCache;
import com.wth.chat.common.websocket.service.WebSocketService;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private BlackDao blackDao;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserCache userCache;

    @Autowired
    private UserSummaryCache userSummaryCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long register(User user) {
        userDao.save(user);
        // 发送用户注册时间
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, user));
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

    @Override
    public void black(Long uid) {
        User user = userDao.getById(uid);
        Black black = new Black();
        black.setType(BlackTypeEnum.ID.getType());
        black.setTarget(uid.toString());
        blackDao.save(black);
        // 拉黑ip
        if (Objects.isNull(user.getIpInfo())) {
            return;
        }
        blackUserIp(user.getIpInfo().getCreateIp());
        blackUserIp(user.getIpInfo().getUpdateIp());
        // 发送拉黑事件
        applicationEventPublisher.publishEvent(new UserBlackEvent(this, user));
    }

    @Override
    public List<SummeryInfoDTO> getSummeryUserInfo(SummeryInfoReq req) {
        //需要前端同步的uid
        List<Long> uidList = getNeedSyncUidList(req.getReqList());
        //加载用户信息
        Map<Long, SummeryInfoDTO> batch = userSummaryCache.getBatch(uidList);
        return req.getReqList()
                .stream()
                .map(a -> batch.containsKey(a.getUid()) ? batch.get(a.getUid()) : SummeryInfoDTO.skip(a.getUid()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemInfoDTO> getItemInfo(ItemInfoReq req) {
        return req.getReqList().stream().map(a -> {
            ItemConfig itemConfig = itemCache.getById(a.getItemId());
            if (Objects.nonNull(a.getLastModifyTime()) && a.getLastModifyTime() >= itemConfig.getUpdateTime().getTime()) {
                return ItemInfoDTO.skip(a.getItemId());
            }
            ItemInfoDTO dto = new ItemInfoDTO();
            dto.setItemId(itemConfig.getId());
            dto.setImg(itemConfig.getImg());
            dto.setDescribe(itemConfig.getDescribe());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<Long> getNeedSyncUidList(List<SummeryInfoReq.InfoReq> reqList) {
        List<Long> needSyncUidList = new ArrayList<>();
        List<Long> userModifyTime = userCache.getUserModifyTime(reqList.stream().map(SummeryInfoReq.InfoReq::getUid).collect(Collectors.toList()));
        for (int i = 0; i < reqList.size(); i++) {
            SummeryInfoReq.InfoReq infoReq = reqList.get(i);
            Long modifyTime = userModifyTime.get(i);
            if (Objects.isNull(infoReq.getLastModifyTime()) || (Objects.nonNull(modifyTime) && modifyTime > infoReq.getLastModifyTime())) {
                needSyncUidList.add(infoReq.getUid());
            }
        }
        return needSyncUidList;
    }

    private void blackUserIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return;
        }
        Black oldBlack = blackDao.getByTargetAndType(ip, BlackTypeEnum.IP);
        if (Objects.nonNull(oldBlack)) {
            return;
        }
        Black black = new Black();
        black.setType(BlackTypeEnum.IP.getType());
        black.setTarget(ip);
        blackDao.save(black);
    }

    private void fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildAuthorizeUser(uid, userInfo);
        user.setName(userInfo.getNickname());
        user.setAvatar(userInfo.getHeadImgUrl());
        userDao.updateById(user);
    }
}




