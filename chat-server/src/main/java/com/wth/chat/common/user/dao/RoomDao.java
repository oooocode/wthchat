package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.Room;
import com.wth.chat.common.user.mapper.RoomMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 房间表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class RoomDao extends ServiceImpl<RoomMapper, Room> {

}
