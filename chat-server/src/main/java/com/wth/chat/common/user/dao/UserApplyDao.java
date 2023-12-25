package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.UserApply;
import com.wth.chat.common.user.mapper.UserApplyMapper;
import com.wth.chat.common.user.service.IUserApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

}
