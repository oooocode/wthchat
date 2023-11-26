package com.wth.chat.common;

import com.wth.chat.common.common.utils.JwtUtils;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.enums.IdempotentEnum;
import com.wth.chat.common.user.domain.enums.ItemEnum;
import com.wth.chat.common.user.mapper.UserMapper;
import com.wth.chat.common.user.service.LoginService;
import com.wth.chat.common.user.service.UserBackpackService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 测试类必须在主程序关闭才可以启动否则 会开启第二个websocket 会导致端口占用
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestDB {

    public static final Long MY_UID = 11006L;
    @Resource
    private UserMapper userMapper;


    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private WxMpService wxMpService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserBackpackService userBackpackService;

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

    @Test
    public void tokenTest() {
        String s = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjExMDA2LCJjcmVhdGVUaW1lIjoxNzAwNjcwODA4fQ.KZ5u4cv7mLXbuNaU5Ysv81gJ02IDIP9aM8PljUWyth0";
        Long validUid = loginService.getValidUid(s);
        System.out.println("validUid = " + validUid);
    }

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void testThread() throws InterruptedException {
        threadPoolTaskExecutor.execute(() -> {
            if (true) {
                log.error("1111");
                throw new RuntimeException("111");
            }
        });
        Thread.sleep(200);
    }

    @Test
    public void testAcquireItem() {
        userBackpackService.acquireItem(MY_UID, ItemEnum.PLANET.getId(), IdempotentEnum.UID, MY_UID.toString());
    }

}