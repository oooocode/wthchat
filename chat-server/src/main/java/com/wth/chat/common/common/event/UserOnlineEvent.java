package com.wth.chat.common.common.event;

import com.wth.chat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 23:48
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {

    private User user;

    public UserOnlineEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
