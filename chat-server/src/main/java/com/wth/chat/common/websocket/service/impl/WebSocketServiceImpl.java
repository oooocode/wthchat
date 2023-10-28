package com.wth.chat.common.websocket.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.wth.chat.common.websocket.domain.dto.WSChannelExtraDTO;
import com.wth.chat.common.websocket.domain.enums.WSRespTypeEnum;
import com.wth.chat.common.websocket.domain.vo.resp.WSBaseResp;
import com.wth.chat.common.websocket.domain.vo.resp.WSLoginUrl;
import com.wth.chat.common.websocket.service.WebSocketService;
import com.wth.chat.common.websocket.service.adapter.WebSocketAdapter;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 专门处理WebSocket的逻辑，包括推拉
 *
 * @Create: 2023/10/28 - 22:34
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Resource
    private WxMpService wxMpService;

    /**
     * 管理所有用户的连接(用户/游客)
     */
    public static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();


    public static final int MAXIMUM_SIZE = 10000;
    public static final Duration DURATION = Duration.ofHours(1);

    /**
     * 临时保存登录code和channel的映射关系
     */
    public static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();

    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());
    }

    @SneakyThrows
    @Override
    public void handleLoginReq(Channel channel) {
        // 生成随机码
        Integer code = generateCode(channel);
        // 向微信平台请求二维码
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.getSeconds());
        // 向前端推送二维码
        sendMsg(channel, WebSocketAdapter.build(wxMpQrCodeTicket));
    }

    @Override
    public void remove(Channel channel) {
        ONLINE_WS_MAP.remove(channel);
        // todo 用戶下线
    }

    private void sendMsg(Channel channel, WSBaseResp<?> wsBaseResp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsBaseResp)));
    }

    private Integer generateCode(Channel channel) {
        Integer code = null;
        do {
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (Objects.nonNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }

}
