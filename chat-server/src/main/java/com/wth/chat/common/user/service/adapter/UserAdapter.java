package com.wth.chat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanCopier;
import com.wth.chat.common.common.enums.YesOrNoEnum;
import com.wth.chat.common.user.domain.entity.ItemConfig;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.entity.UserBackpack;
import com.wth.chat.common.user.domain.vo.resp.BadgeResp;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static List<BadgeResp> buildBadge(List<ItemConfig> itemList, List<UserBackpack> userItems, User user) {
        Set<Long> userItemSet = userItems.stream().map(UserBackpack::getItemId).collect(Collectors.toSet());
        return itemList.stream().map(e -> {
                    BadgeResp badgeResp = BeanUtil.copyProperties(e, BadgeResp.class);
                    badgeResp.setObtain(userItemSet.contains(e.getId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
                    badgeResp.setWearing(Objects.equals(user.getItemId(), e.getId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
                    return badgeResp;
                }).sorted(Comparator.comparing(BadgeResp::getWearing, Comparator.reverseOrder())
                        .thenComparing(BadgeResp::getObtain, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
