package com.wth.chat.common.user.service.impl;

import com.wth.chat.common.user.domain.entity.UserRole;
import com.wth.chat.common.user.domain.enums.RoleEnum;
import com.wth.chat.common.user.service.RoleService;
import com.wth.chat.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-12-18
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserCache userCache;

    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        Set<Long> roleIds = userCache.listByUid(uid);
        return isAdmin(roleIds) | roleIds.contains(roleEnum.getId());
    }

    private boolean isAdmin(Set<Long> roleIds) {
        return roleIds.contains(RoleEnum.ADMIN.getId());
    }

}
