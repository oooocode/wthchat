package com.wth.chat.common.common.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * @Author: wth
 * @Create: 2023/11/24 - 17:07
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {

    public static final MyUncaughtExceptionHandler MY_CAUGHT_EXCEPTION_HANDLER = new MyUncaughtExceptionHandler();
    private ThreadFactory origin;

    @Override
    public Thread newThread(Runnable r) {
        // 装饰器模式，包装原来的线程工厂
        Thread thread = origin.newThread(r);
        thread.setUncaughtExceptionHandler(MY_CAUGHT_EXCEPTION_HANDLER);
        return thread;
    }
}
