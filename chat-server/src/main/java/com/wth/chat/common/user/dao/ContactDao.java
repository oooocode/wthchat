package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.Contact;
import com.wth.chat.common.user.mapper.ContactMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会话列表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {

}
