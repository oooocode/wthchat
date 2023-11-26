package com.wth.chat.common.user.service;


import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.vo.resp.BadgeResp;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.List;

/**
* @author 29977
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-10-18 21:32:24
*/
public interface UserService  {



    Long register(User insert);

    void authorize(WxOAuth2UserInfo userInfo);

    UserInfoResp getUserInfo(Long uid);

    void modifyName(Long uid, String name);

    List<BadgeResp> badges(Long uid);

    void wearingBadge(Long uid, Long itemId);
}
