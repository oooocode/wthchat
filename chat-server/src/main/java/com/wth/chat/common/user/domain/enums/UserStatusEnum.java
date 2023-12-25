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
public enum UserStatusEnum {

    NORMAL(1, "正常"),
    BLACK(2, "拉黑"),
    ;


    private final Integer status;
    private final String desc;

    private static Map<Integer, UserStatusEnum> cache;

    static {
        cache = Arrays.stream(UserStatusEnum.values()).collect(Collectors.toMap(UserStatusEnum::getStatus, Function.identity()));
    }

    public static UserStatusEnum of(Integer status) {
        return cache.get(status);
    }

}
