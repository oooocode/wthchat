package com.wth.chat.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 16:39
 */
@ApiModel("用户信息")
@Data
public class UserInfoResp {

    @ApiModelProperty("uid")
    private Long uid;
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("用户性别")
    private Integer sex;
    @ApiModelProperty("剩余改名次数")
    private Integer modifyNameChance;
}
