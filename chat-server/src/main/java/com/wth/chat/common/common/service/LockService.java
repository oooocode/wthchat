package com.wth.chat.common.common.service;

import com.sun.org.apache.bcel.internal.generic.LCONST;
import com.wth.chat.common.common.exception.BusinessException;
import com.wth.chat.common.common.exception.CommonErrorEnum;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.cache.LocalCachedMapDisableAck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 编程式分布式锁工具类
 * @Author: wth
 * @Create: 2023/11/26 - 22:01
 */
@Component
public class LockService {

    @Autowired
    private RedissonClient redissonClient;

    @SneakyThrows
    public <T> T executeWithLock(String key, long time, TimeUnit timeUnit, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock(time, timeUnit);
        if (!success) {
            throw new BusinessException(CommonErrorEnum.LOCK_LIMIT);
        }
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    public <T> T executeWithLock(String key,  Supplier<T> supplier) {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, supplier);
    }

    public <T> T executeWithLock(String key, Runnable runnable) {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, () -> {
            runnable.run();
            return null;
        });
    }
}
