package com.wth.chat.common.user.service;

import com.wth.chat.common.user.domain.entity.RoomFriend;

import java.util.List;

/**
 * <p>
 * 房间表 服务类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
public interface RoomService {

    /**
     * 禁用房间
     * @param asList
     */
    void disableFriendRoom(List<Long> asList);

    /**
     * 创建一个单聊房间
     */
    RoomFriend createFriendRoom(List<Long> asList);
}
