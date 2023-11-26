package com.wth.chat.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 16:39
 */
@ApiModel("徽章信息")
@Data
public class BadgeResp {

    @ApiModelProperty("itemId")
    private Long id;
    @ApiModelProperty("徽章图片")
    private String img;
    @ApiModelProperty("徽章描述")
    private String describe;
    @ApiModelProperty("是否拥有 0-否 1-是")
    private Integer obtain;
    @ApiModelProperty("是否佩戴 0-否 1-是")
    private Integer wearing;
}
