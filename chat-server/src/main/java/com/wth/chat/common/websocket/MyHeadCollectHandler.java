package com.wth.chat.common.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import com.wth.chat.common.websocket.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import jodd.util.StringUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Optional;

/**
 * 请求参数处理器
 * 用户处理第一次握手的校验
 *
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
            // 获取ip(nginx)
            String ip = request.headers().get("X-Real-IP");
            if (StringUtil.isBlank(ip)) {
                // 直连 ip
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                ip = address.getAddress().getHostAddress();
            }
            // 添加到channel附件
            NettyUtil.setAttr(ctx.channel(), NettyUtil.IP, ip);
            // 处理器只需要用一次
            ctx.pipeline().remove(this);
        }
        ctx.fireChannelRead(msg);
    }
}
