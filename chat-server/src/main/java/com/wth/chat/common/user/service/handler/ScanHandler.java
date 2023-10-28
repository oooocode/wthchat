package com.wth.chat.common.user.service.handler;

import com.wth.chat.common.user.service.adapter.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

@Component
public class ScanHandler extends AbstractHandler {

    @Value("${wx.mp.callback}")
    private String callback;

    public static final String REDIRECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String eventKey = wxMpXmlMessage.getEventKey();
        String openId = wxMpXmlMessage.getFromUser();
        String authorizeUrl = String.format(REDIRECT_URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callback"));
        // todo 扫码事件处理
        return TextBuilder.build("你好请登录: <a href=\"" + authorizeUrl + "\">登录</a>", wxMpXmlMessage);
    }

}
