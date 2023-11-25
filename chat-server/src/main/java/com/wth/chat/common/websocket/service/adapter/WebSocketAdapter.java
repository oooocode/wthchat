package com.wth.chat.common.websocket.service.adapter;

import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.websocket.domain.enums.WSRespTypeEnum;
import com.wth.chat.common.websocket.domain.vo.resp.WSBaseResp;
import com.wth.chat.common.websocket.domain.vo.resp.WSLoginSuccess;
import com.wth.chat.common.websocket.domain.vo.resp.WSLoginUrl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * @Author: wth
 * @Create: 2023/10/28 - 23:43
 */
public class WebSocketAdapter {
    public static WSBaseResp<?> build(WxMpQrCodeTicket wxMpQrCodeTicket) {
        // 返回二维码
        WSBaseResp<WSLoginUrl> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        wsBaseResp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return wsBaseResp;
    }

    public static WSBaseResp<?> build(User user, String token) {
        WSBaseResp<WSLoginSuccess> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess data = WSLoginSuccess.builder()
                .name(user.getName())
                .avatar(user.getAvatar())
                .uid(user.getId())
                .token(token)
                .build();
        wsBaseResp.setData(data);
        return wsBaseResp;
    }

    public static WSBaseResp<?> buildWaitAuthorize() {
        WSBaseResp<WSLoginUrl> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return wsBaseResp;
    }

    public static WSBaseResp<?> buildAuthorizeFail() {
        WSBaseResp<WSLoginUrl> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.INVALIDATE_TOKEN.getType());
        return wsBaseResp;
    }
}
