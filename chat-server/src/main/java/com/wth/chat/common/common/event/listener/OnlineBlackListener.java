package com.wth.chat.common.common.event.listener;

import com.wth.chat.common.common.event.UserBlackEvent;
import com.wth.chat.common.common.event.UserOnlineEvent;
import com.wth.chat.common.user.dao.UserDao;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.domain.enums.UserActiveStatusEnum;
import com.wth.chat.common.user.service.IpService;
import com.wth.chat.common.user.service.cache.UserCache;
import com.wth.chat.common.websocket.domain.vo.resp.WSBaseResp;
import com.wth.chat.common.websocket.service.WebSocketService;
import com.wth.chat.common.websocket.service.adapter.WebSocketAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 23:49
 */
@Component
public class OnlineBlackListener {
    @Autowired
    private UserDao userDao;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private UserCache userCache;

    @TransactionalEventListener(value = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void sendMsg(UserBlackEvent userBlackEvent) {
        User user = userBlackEvent.getUser();
        webSocketService.sendMsgToAll(WebSocketAdapter.buildBlack(user.getId()));
    }

    @TransactionalEventListener(value = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void changeUserStatus(UserBlackEvent userBlackEvent) {
        User user = userBlackEvent.getUser();
        userDao.blackUser(user.getId());
    }

    @TransactionalEventListener(value = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void evictBlackList(UserBlackEvent userBlackEvent) {
        userCache.evictBlack();
    }




}
