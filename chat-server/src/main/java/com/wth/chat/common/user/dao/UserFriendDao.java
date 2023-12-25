package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.UserFriend;
import com.wth.chat.common.user.mapper.UserFriendMapper;
import com.wth.chat.common.user.service.IUserFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户联系人表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {

}
