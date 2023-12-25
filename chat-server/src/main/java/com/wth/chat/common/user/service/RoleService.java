package com.wth.chat.common.user.service;

import com.wth.chat.common.user.domain.entity.UserRole;
import com.wth.chat.common.user.domain.enums.RoleEnum;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-12-18
 */
public interface RoleService {


    /**
     * 是否有某种权限
     *
     * @param uid
     * @param roleEnum
     * @return
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);

}
