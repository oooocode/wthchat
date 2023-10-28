package com.wth.chat.common.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wth.chat.common.user.domain.entity.User;
import com.wth.chat.common.user.mapper.UserMapper;
import com.wth.chat.common.user.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 29977
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-10-18 21:32:24
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




