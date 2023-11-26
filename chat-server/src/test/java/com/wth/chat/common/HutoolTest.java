package com.wth.chat.common;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试类必须在主程序关闭才可以启动否则 会开启第二个websocket 会导致端口占用
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class HutoolTest {

    @Autowired
    private HttpServletRequest request;

    @Test
    public void testIp() throws InterruptedException {
        String clientIP = ServletUtil.getClientIP(request);
        System.out.println("clientIP = " + clientIP);
    }

}