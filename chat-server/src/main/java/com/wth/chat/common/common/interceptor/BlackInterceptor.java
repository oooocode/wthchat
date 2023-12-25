package com.wth.chat.common.common.interceptor;

import com.wth.chat.common.common.exception.HttpErrorEnum;
import com.wth.chat.common.common.utils.RequestHolder;
import com.wth.chat.common.user.dao.BlackDao;
import com.wth.chat.common.user.domain.enums.BlackTypeEnum;
import com.wth.chat.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 17:57
 */
@Component
public class BlackInterceptor implements HandlerInterceptor {

    @Autowired
    private UserCache userCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<Integer, Set<String>> blackSet = userCache.listBlackMap();
        if (inBlackList(RequestHolder.getUid(), blackSet.get(BlackTypeEnum.ID.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
        }
        if (inBlackList(RequestHolder.getIp(), blackSet.get(BlackTypeEnum.IP.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
        }
        return true;
    }

    private boolean inBlackList(Object target, Set<String> blackSet) {
        if (target == null || CollectionUtils.isEmpty(blackSet)) {
            return false;
        }
        return blackSet.contains(target.toString());
    }


}
