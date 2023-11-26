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
public enum IdempotentEnum {

    UID(1, "用户id"),
    MSG_ID(2, "消息id"),
    ;


    private final Integer type;
    private final String desc;

    private static Map<Integer, IdempotentEnum> cache;

    static {
        cache = Arrays.stream(IdempotentEnum.values()).collect(Collectors.toMap(IdempotentEnum::getType, Function.identity()));
    }

    public static IdempotentEnum of(Integer type) {
        return cache.get(type);
    }

}
