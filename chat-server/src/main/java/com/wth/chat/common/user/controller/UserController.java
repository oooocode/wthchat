package com.wth.chat.common.user.controller;


import com.wth.chat.common.common.utils.RequestHolder;
import com.wth.chat.common.user.domain.vo.resp.ApiResult;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-10-22
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/capi/user")
public class UserController {

    @GetMapping("/public/userInfo")
    @ApiOperation("获取用户信息")
    public ApiResult<UserInfoResp> getUserInfo() {
        Long uid = RequestHolder.getUid();
        return null;
    }


}

