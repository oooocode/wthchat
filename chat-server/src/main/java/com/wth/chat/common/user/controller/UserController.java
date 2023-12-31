package com.wth.chat.common.user.controller;


import com.wth.chat.common.common.utils.AssertUtil;
import com.wth.chat.common.common.utils.RequestHolder;
import com.wth.chat.common.user.domain.dto.ItemInfoDTO;
import com.wth.chat.common.user.domain.dto.SummeryInfoDTO;
import com.wth.chat.common.user.domain.enums.RoleEnum;
import com.wth.chat.common.user.domain.vo.req.*;
import com.wth.chat.common.user.domain.vo.resp.ApiResult;
import com.wth.chat.common.user.domain.vo.resp.BadgeResp;
import com.wth.chat.common.user.domain.vo.resp.UserInfoResp;
import com.wth.chat.common.user.service.RoleService;
import com.wth.chat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @Autowired
    private RoleService roleService;
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

    @PostMapping("/public/summary/userInfo/batch")
    @ApiOperation("用户聚合信息-返回的代表需要刷新的")
    public ApiResult<List<SummeryInfoDTO>> getSummeryUserInfo(@Valid @RequestBody SummeryInfoReq req) {
        return ApiResult.success(userService.getSummeryUserInfo(req));
    }

    @PostMapping("/public/badges/batch")
    @ApiOperation("徽章聚合信息-返回的代表需要刷新的")
    public ApiResult<List<ItemInfoDTO>> getItemInfo(@Valid @RequestBody ItemInfoReq req) {
        return ApiResult.success(userService.getItemInfo(req));
    }


    @GetMapping("/badges")
    @ApiOperation("获取徽章")
    public ApiResult<List<BadgeResp>> badges() {
        return ApiResult.success(userService.badges(RequestHolder.getUid()));
    }

    @PostMapping("/wearing")
    @ApiOperation("获取徽章")
    public ApiResult<Void> wearingBadge(@RequestBody @Valid WearingBadgeReq wearingBadgeReq) {
        userService.wearingBadge(RequestHolder.getUid(), wearingBadgeReq.getItemId());
        return ApiResult.success();
    }

    @PostMapping("/black")
    @ApiOperation("拉黑用户")
    public ApiResult<Void> blackUser(@RequestBody @Valid BlackUserReq blackUserReq) {
        boolean isManager = roleService.hasPower(RequestHolder.getUid(), RoleEnum.CHAT_MANAGER);
        AssertUtil.isTrue(isManager, "没有管理权限");
        userService.black(blackUserReq.getUid());
        return ApiResult.success();
    }



}

