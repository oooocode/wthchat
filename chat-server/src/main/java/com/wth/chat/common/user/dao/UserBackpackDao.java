package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.UserBackpack;
import com.wth.chat.common.user.mapper.UserBackpackMapper;
import com.wth.chat.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-11-25
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> implements IUserBackpackService {

}
