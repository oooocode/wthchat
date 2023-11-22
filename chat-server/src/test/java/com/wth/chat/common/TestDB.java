package com.wth.chat.common;

import com.wth.chat.common.common.config.RedissonConfig;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.mapper.UserMapper;
import com.wth.chat.common.common.utils.JwtUtils;
import com.wth.chat.common.common.utils.RedisUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
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
    private JwtUtils jwtUtils;

    @Resource
    private WxMpService wxMpService;

    @Autowired
    private RedissonClient redissonClient;

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

    @Test
    public void testJwt() {
        String token = jwtUtils.createToken(1L);
        System.out.println(token);
        System.out.println(jwtUtils.getUidOrNull(token));
    }


    @Test
    public void redis() {
        RLock lock = redissonClient.getLock("123");
        lock.lock();
        System.out.println(111);
        lock.unlock();
    }


}