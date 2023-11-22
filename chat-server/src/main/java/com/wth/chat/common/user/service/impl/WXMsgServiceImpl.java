package com.wth.chat.common.user.service.impl;

import com.wth.chat.common.user.dao.UserDao;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.service.UserService;
import com.wth.chat.common.user.service.WXMsgService;
import com.wth.chat.common.user.service.adapter.TextBuilder;
import com.wth.chat.common.user.service.adapter.UserAdapter;
import com.wth.chat.common.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wth
 * @Create: 2023/11/1 - 22:47
 */
@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {

    public static final String REDIRECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    /**
     * openid 和 code 的关系map
     */
    public static final ConcurrentHashMap<String, Integer> WAIT_AUTHORIZE_MAP = new ConcurrentHashMap<>();
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    @Lazy
    private WxMpService wxMpService;
    @Value("${wx.mp.callback}")
    private String callback;


    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMessage) {
        String openId = wxMessage.getFromUser();
        Integer code = getEventKey(wxMessage);
        if (Objects.isNull(code)) {
            return null;
        }
        User user = userDao.getByOpenId(openId);
        boolean registered = Objects.nonNull(user);
        boolean authorized = registered && StringUtils.isNotBlank(user.getAvatar());
        if (registered && authorized) {
            // 登录成功逻辑 使用 code 关联 channel 通知前端登录成功
            webSocketService.scanLoginSuccess(code, user.getId());
            return null;
        }
        if (!registered) {
            User insert = UserAdapter.buildUser(openId);
            userService.register(insert);
        }
        // 扫码成功让用户授权
        WAIT_AUTHORIZE_MAP.put(openId, code);
        // 向前端推送待授权状态
        webSocketService.waitAuthorize(code);
        String authorizeUrl = String.format(REDIRECT_URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callback"));
        return TextBuilder.build("你好请登录: <a href=\"" + authorizeUrl + "\">登录</a>", wxMessage);
    }

    private Integer getEventKey(WxMpXmlMessage wxMessage) {
        try {
            String eventKey = wxMessage.getEventKey();
            // 二维码时间码前缀为qrscene_
            String code = eventKey.replace("qrscene_", "");
            return Integer.parseInt(code);
        } catch (Exception e) {
            log.error("getEventKey error eventKey: {}", wxMessage.getEventKey(), e);
            return null;
        }
    }
}
