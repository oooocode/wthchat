package com.wth.chat.common.user.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @Author: wth
 * @Create: 2023/11/1 - 22:47
 */
public interface WXMsgService {
    /**
     * 用户扫码事件
     * @param wxMessage
     * @return
     */
    WxMpXmlOutMessage scan(WxMpXmlMessage wxMessage);
}
