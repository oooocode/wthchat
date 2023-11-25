package com.wth.chat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanCopier;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * @Author: wth
 * @Create: 2023/11/1 - 23:05
 */
public class UserAdapter {
    public static User buildUser(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildAuthorizeUser(Long id, WxOAuth2UserInfo userInfo) {
        return User.builder()
                .id(id)
                .name(userInfo.getNickname())
                .avatar(userInfo.getHeadImgUrl())
                .build();
    }

    public static UserInfoResp buildUserInfo(User user, Integer modifyNameCount) {
        UserInfoResp userInfoResp = BeanUtil.copyProperties(user, UserInfoResp.class);
        userInfoResp.setModifyNameChance(modifyNameCount);
        return userInfoResp;
    }
}
