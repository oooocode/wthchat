package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.GroupMember;
import com.wth.chat.common.user.mapper.GroupMemberMapper;
import com.wth.chat.common.user.service.IGroupMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群成员表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {

}
