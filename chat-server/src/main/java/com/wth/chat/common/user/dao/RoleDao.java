package com.wth.chat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wth.chat.common.user.domain.entity.Role;
import com.wth.chat.common.user.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-12-18
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
