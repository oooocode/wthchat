package com.wth.chat.common.websocket.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: wth
 * @Create: 2023/10/28 - 22:35
 */
public interface WebSocketService {

    void connect(Channel channel);

    void handleLoginReq(Channel channel);

    void remove(Channel channel);

}
