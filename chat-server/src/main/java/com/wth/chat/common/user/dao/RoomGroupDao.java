package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.RoomGroup;
import com.wth.chat.common.user.mapper.RoomGroupMapper;
import com.wth.chat.common.user.service.IRoomGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群聊房间表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class RoomGroupDao extends ServiceImpl<RoomGroupMapper, RoomGroup> {

}
