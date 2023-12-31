package com.wth.chat.common.user.service.adapter;

import com.wth.chat.common.user.domain.entity.Room;
import com.wth.chat.common.user.domain.entity.RoomFriend;
import com.wth.chat.common.user.domain.enums.NormalOrNoEnum;
import com.wth.chat.common.user.domain.enums.RoomTypeEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wth
 * @Create: 2023/12/31 - 20:24
 */
public class ChatAdapter {

    public static final String SEPARATOR = ",";

    public static String generateRoomKey(List<Long> uidList) {
        return uidList.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    public static RoomFriend buildFriendRoom(Long roomId, List<Long> uidList) {
        List<Long> collect = uidList.stream().sorted().collect(Collectors.toList());
        RoomFriend roomFriend = new RoomFriend();
        roomFriend.setRoomId(roomId);
        roomFriend.setUid1(collect.get(0));
        roomFriend.setUid2(collect.get(1));
        roomFriend.setRoomKey(generateRoomKey(uidList));
        roomFriend.setStatus(NormalOrNoEnum.NORMAL.getStatus());
        return roomFriend;
    }

    public static Room buildRoom(RoomTypeEnum typeEnum) {
        Room room = new Room();
        room.setType(typeEnum.getType());
        room.setHotFlag(HotFlagEnum.NOT.getType());
        return room;
    }
}
