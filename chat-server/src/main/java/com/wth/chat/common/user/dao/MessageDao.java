package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.Message;
import com.wth.chat.common.user.mapper.MessageMapper;
import com.wth.chat.common.user.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

}
