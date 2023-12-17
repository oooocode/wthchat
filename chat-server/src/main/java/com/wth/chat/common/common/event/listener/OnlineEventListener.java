package com.wth.chat.common.common.event.listener;

import com.wth.chat.common.common.event.UserOnlineEvent;
import com.wth.chat.common.common.event.UserRegisterEvent;
import com.wth.chat.common.user.dao.UserDao;
import com.wth.chat.common.user.domain.entity.IpInfo;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.enums.IdempotentEnum;
import com.wth.chat.common.user.domain.enums.ItemEnum;
import com.wth.chat.common.user.domain.enums.UserActiveStatusEnum;
import com.wth.chat.common.user.service.IpService;
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
public class OnlineEventListener {
    @Autowired
    private UserDao userDao;

    @Autowired
    private IpService ipService;

    @TransactionalEventListener(value = UserOnlineEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void saveDB(UserOnlineEvent userOnlineEvent) {
        User user = userOnlineEvent.getUser();
        User update = new User();
        update.setId(user.getId());
        update.setLastOptTime(user.getLastOptTime());
        update.setIpInfo(user.getIpInfo());
        update.setActiveStatus(UserActiveStatusEnum.ONLINE.getStatus());
        userDao.updateById(update);
        // 解析ip信息
        ipService.refreshIpDetailAsync(user.getId());
    }




}
