package com.wth.chat.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wth
 * @Create: 2023/12/31 - 20:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendApplyResp {
    @ApiModelProperty("申请id")
    private Long applyId;

    @ApiModelProperty("申请人uid")
    private Long uid;

    @ApiModelProperty("申请类型 1加好友")
    private Integer type;

    @ApiModelProperty("申请信息")
    private String msg;

    @ApiModelProperty("申请状态 1待审批 2同意")
    private Integer status;
}
