package com.wth.chat.common.websocket.service;

import io.netty.channel.Channel;

/**
 * @Author: wth
 * @Create: 2023/10/28 - 22:35
 */
public interface WebSocketService {

    void connect(Channel channel);

    void handleLoginReq(Channel channel);

    void remove(Channel channel);

    void scanLoginSuccess(Integer code, Long id);

    void waitAuthorize(Integer code);

    void authorize(Channel channel, String token);
}
