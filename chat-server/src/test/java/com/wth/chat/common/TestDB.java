package com.wth.chat.common;

import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.mapper.UserMapper;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 测试类必须在主程序关闭才可以启动否则 会开启第二个websocket 会导致端口占用
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDB {

    @Resource
    private UserMapper userMapper;

    @Resource
    private WxMpService wxMpService;

    @Test
    public void test() {
        User user = new User();
        user.setName("1231");
        user.setOpenId("1231");
        int insert = userMapper.insert(user);
        Assert.assertEquals(1, insert);
    }

    @Test
    public void testWX() throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 100000);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println("url = " + url);
    }
}