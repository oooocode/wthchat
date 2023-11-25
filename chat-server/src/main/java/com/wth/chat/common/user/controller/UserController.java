package com.wth.chat.common.user.controller;


import com.wth.chat.common.common.utils.RequestHolder;
import com.wth.chat.common.user.domain.vo.req.ModifyNameReq;
import com.wth.chat.common.user.domain.vo.resp.ApiResult;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import com.wth.chat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo(RequestHolder.getUid()));
    }

    @PostMapping("/modifyName")
    @ApiOperation("修改用户名称")
    public ApiResult<Void> modifyName(@RequestBody @Valid ModifyNameReq modifyNameReq) {
        userService.modifyName(RequestHolder.getUid(), modifyNameReq.getName());
        return ApiResult.success();
    }



}

