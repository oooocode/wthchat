package com.wth.chat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 21:27
 */
@AllArgsConstructor
@Getter
public enum UserActiveStatusEnum {

    ONLINE(1, "在线"),
    OFFLINE(2, "离线"),
    ;


    private final Integer status;
    private final String desc;

    private static Map<Integer, UserActiveStatusEnum> cache;

    static {
        cache = Arrays.stream(UserActiveStatusEnum.values()).collect(Collectors.toMap(UserActiveStatusEnum::getStatus, Function.identity()));
    }

    public static UserActiveStatusEnum of(Integer status) {
        return cache.get(status);
    }

}
