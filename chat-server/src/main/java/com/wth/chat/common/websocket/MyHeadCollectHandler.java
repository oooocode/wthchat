package com.wth.chat.common.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import com.wth.chat.common.websocket.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Optional;

/**
 * 请求参数处理器
 * 用户处理第一次握手的校验
 * @Author: wth
 * @Create: 2023/11/25 - 15:01
 */
public class MyHeadCollectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            Optional<String> tokenOptional = Optional.of(urlBuilder)
                    .map(UrlBuilder::getQuery)
                    .map(item -> item.get("token"))
                    .map(CharSequence::toString);
            tokenOptional.ifPresent(token -> NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, token));
            // 拿到 token 需要去掉, url后所有参数，否则后面的处理器会匹配不到url
            request.setUri(urlBuilder.getPath().toString());
        }
        ctx.fireChannelRead(msg);
    }
}
