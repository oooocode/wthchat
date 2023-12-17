package com.wth.chat.common.common.event.listener;

import com.wth.chat.common.common.event.UserRegisterEvent;
import com.wth.chat.common.user.dao.UserDao;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.enums.IdempotentEnum;
import com.wth.chat.common.user.domain.enums.ItemEnum;
import com.wth.chat.common.user.service.UserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 23:49
 */
@Component
public class RegisterEventListener {

    @Autowired
    private UserBackpackService userBackpackService;

    @Autowired
    private UserDao userDao;

    @TransactionalEventListener(value = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendCard(UserRegisterEvent userRegisterEvent) {
        User user = userRegisterEvent.getUser();
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
    }

    @TransactionalEventListener(value = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendBadge(UserRegisterEvent userRegisterEvent) {
        User user = userRegisterEvent.getUser();
        int registerCount = userDao.count();
        if (registerCount < 10) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        } else if (registerCount < 100) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        }
    }


}
