package com.wth.chat.common.user.service.impl;

import com.wth.chat.common.common.annotation.RedissonLock;
import com.wth.chat.common.common.enums.YesOrNoEnum;
import com.wth.chat.common.common.service.LockService;
import com.wth.chat.common.user.dao.UserBackpackDao;
import com.wth.chat.common.user.domain.entity.UserBackpack;
import com.wth.chat.common.user.domain.enums.IdempotentEnum;
import com.wth.chat.common.user.service.UserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 21:30
 */
@Component
public class UserBackpackServiceImpl implements UserBackpackService {

    @Autowired
    private UserBackpackDao userBackpackDao;

    @Autowired
    private LockService lockService;

    @Autowired
    @Lazy
    private UserBackpackServiceImpl userBackpackService;


    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        String idempotent = getIdempotentId(itemId, idempotentEnum, businessId);
        userBackpackService.doAcquireItem(uid, itemId, idempotent);
    }

    @RedissonLock(key = "#idempotent", waitTime = 5000)
    public void doAcquireItem(Long uid, Long itemId, String idempotent) {
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        // 幂等检查
        if (Objects.nonNull(userBackpack)) {
            return;
        }
        // 发放物品
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesOrNoEnum.NO.getStatus())
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
    }

    private String getIdempotentId(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }
}
