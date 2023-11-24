package com.wth.chat.common.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义线程异常回调处理器
 * @Author: wth
 * @Create: 2023/11/24 - 17:01
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread", e);
    }
}
