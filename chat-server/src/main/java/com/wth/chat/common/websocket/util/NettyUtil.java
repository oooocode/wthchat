package com.wth.chat.common.websocket.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;


/**
 * @Author: wth
 * @Create: 2023/11/25 - 15:02
 */
public class NettyUtil {

    public static final AttributeKey<String> TOKEN = AttributeKey.valueOf("token");
    public static final AttributeKey<String> IP = AttributeKey.valueOf("ip");

    public static <T> void setAttr(Channel channel, AttributeKey<T> attributeKey, T value) {
        Attribute<T> attr = channel.attr(attributeKey);
        attr.set(value);
    }

    public static <T> T getAttr(Channel channel, AttributeKey<T> attributeKey) {
        Attribute<T> attr = channel.attr(attributeKey);
        return attr.get();
    }
}
