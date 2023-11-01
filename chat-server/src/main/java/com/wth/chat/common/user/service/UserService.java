package com.wth.chat.common.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wth.chat.common.user.domain.entity.User;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
* @author 29977
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-10-18 21:32:24
*/
public interface UserService  {


    Long register(User insert);

    void authorize(WxOAuth2UserInfo userInfo);

}
